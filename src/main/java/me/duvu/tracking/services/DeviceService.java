package me.duvu.tracking.services;

import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.entities.Account;
import me.duvu.tracking.entities.AlertProfile;
import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.enumeration.DeviceStatus;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.exception.DeviceAlreadyExistedException;
import me.duvu.tracking.exception.InvalidModelException;
import me.duvu.tracking.exception.ObjectNotFoundException;
import me.duvu.tracking.internal.websocket.LocalWSService;
import me.duvu.tracking.internal.websocket.cmd.CmdModel;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.repository.AlertProfileRepository;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.specification.DeviceSpecification;
import me.duvu.tracking.web.rest.model.request.DeviceRequest;
import me.duvu.tracking.web.rest.model.request.ToggleAlertProfileDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author beou on 8/1/17 03:33
 */

@Slf4j
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final AccountRepository accountRepository;
    private final AlertProfileRepository alertProfileRepository;
    private final DeviceSpecification deviceSpecification;

    private final LocalWSService localWSService;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository,
                         AccountRepository accountRepository,
                         DeviceSpecification deviceSpecification,
                         AlertProfileRepository alertProfileRepository,
                         LocalWSService localWSService) {
        this.deviceRepository = deviceRepository;
        this.accountRepository = accountRepository;
        this.deviceSpecification = deviceSpecification;
        this.alertProfileRepository = alertProfileRepository;
        this.localWSService = localWSService;
    }

    public Page<Device> getAll(String search, Pageable pageable) {
        Specification<Device> specification = deviceSpecification.search(search);
        Page<Device> devicePage = deviceRepository.findAll(specification, pageable);
        return devicePage.map(x -> {
            Set<AlertProfile> alertProfileSet = x.getAlertProfiles().stream().filter(xa -> xa.getAccount().getId().equals(ApplicationContext.getAccountId())).collect(Collectors.toSet());
            x.setAlertProfiles(alertProfileSet);
            return x;
        });
    }

    public List<Device> getAll(String search) {
        Specification<Device> specification = deviceSpecification.search(search);
        List<Device> deviceList = deviceRepository.findAll(specification);

        // remove alert profile that does not belong to current user
        return deviceList.stream().peek(x -> {
            Set<AlertProfile> alertProfileSet = x.getAlertProfiles().stream().filter(xa -> xa.getAccount().getId().equals(ApplicationContext.getAccountId())).collect(Collectors.toSet());
            x.setAlertProfiles(alertProfileSet);
        }).collect(Collectors.toList());
    }

    public Device getById(Long id) {
        Specification<Device> specification = deviceSpecification.findOne(id);
        return deviceRepository.findOne(specification).orElse(null);
    }

    public Device findByUniqueId(String uniqueId) {
        return deviceRepository.findByDeviceId(uniqueId);
    }

    public List<Device> getAllDeviceByAccountId(Long accountId) {
        Specification<Device> specification = deviceSpecification.queryByAccountId(accountId);
        return deviceRepository.findAll(specification);
    }

    @Transactional
    public Device create(DeviceRequest request) {
        String deviceId = request.getDeviceId();
        //1. find un-assigned device (un-assigned mean no company assigned)
        Device dev = deviceRepository.findByDeviceId(deviceId);
        if (dev != null) {
                throw new DeviceAlreadyExistedException();
        } else {
            Set<Long> accountIds = request.getAccountIds();
            accountIds.add(ApplicationContext.getAccountId()); // add current account to device

            Set<Long> alertProfileIds = request.getAlertProfileIds();
            Set<Account> accountSet = accountIds.stream().map(x -> accountRepository.findById(x).orElse(null)).collect(Collectors.toSet());

            Set<AlertProfile> alertProfileSet = alertProfileIds != null ?
                    alertProfileIds.stream().map(alertProfileRepository::getOne).collect(Collectors.toSet()) : null;

            Date expiredOn = request.getExpiredOn();

            String timezoneId = StringUtils.isEmpty(request.getTimeZoneStr()) ?
                    ApplicationContext.getTimezoneStr() : request.getTimeZoneStr();

            String statusStr = request.getStatus();
            DeviceStatus status = StringUtils.isNotBlank(statusStr) ? DeviceStatus.get(request.getStatus()) : DeviceStatus.ACTIVE;

            Device device = Device.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .deviceId(request.getDeviceId())
                    .imei(request.getImei())
                    .accounts(accountSet)
                    .alertProfiles(alertProfileSet)
                    //.vehicle(null)
                    .protocol(request.getProtocol())
                    .ipAddress(request.getIpAddress())
                    .port(request.getPort())
                    .timeZoneStr(timezoneId)
                    .maxStoredDataTime(request.getMaxStoredDataTime())
                    .expiredOn(expiredOn)
                    .status(status)
                    .serialNumber(request.getSerivalNumber())
                    .modelName(request.getModelName())
                    .manufacturerName(request.getManufacturerName())
                    .firmwareVerison(request.getFirmwareVerison())
                    .originalCountry(request.getOriginalCountry())
                    .createdBy(ApplicationContext.getCurrentUserName())
                    .build();

            return deviceRepository.save(device);
        }
    }

    @Transactional
    public Device save(Device device) {
        if (device == null) {
            throw new InvalidModelException();
        }

        return deviceRepository.save(device);
    }

    @Transactional
    public Device update(Long id, DeviceRequest request) {
        Device device = deviceRepository.getOne(id);
        device = mapping(device, request);
        if (device != null) {
            return deviceRepository.save(device);
        } else {
            throw new ObjectNotFoundException();
        }
    }

    @Transactional
    public Device delete(Long id) {
        Specification<Device> specification = deviceSpecification.findOne(id);
        Device device = deviceRepository.findOne(specification).orElse(null);
        if (device != null) {
            device.setStatus(DeviceStatus.DISABLED);
            deviceRepository.save(device);

            return device;
        } else {
            throw new AccessDeninedOrNotExisted("Cannot delete this device #id: " + id);
        }
    }

    @Transactional

    // disabled mean deleted, cleanup daily or weekly
    public List<Device> getAllDisabled() {
        Specification<Device> specification = deviceSpecification.queryByStatus(DeviceStatus.DISABLED);
        return deviceRepository.findAll(specification);
    }

    public Optional<Device> findOne(Long id) {
        Specification<Device> specification = deviceSpecification.findOne(id);
        return deviceRepository.findOne(specification);
    }

    //-- query by all expired today
    public List<Device> getAllExpiredToday() {
        Specification<Device> specification = deviceSpecification.queryAllExpiredToday();
        return deviceRepository.findAll(specification);
    }

    private Device mapping(Device device, DeviceRequest request) {

        String status = request.getStatus();
        DeviceStatus devStatus = DeviceStatus.get(status);
        Date expiredOn = request.getExpiredOn();

        if (device != null) {
            device.setName(request.getName());
            device.setDescription(request.getDescription());
            device.setDeviceId(request.getDeviceId());
            device.setImei(request.getImei());

            Set<Long> accountIds = request.getAccountIds();
            Set<Account> accountSet = accountIds != null ?
                    accountIds.stream().map(accountRepository::getOne).collect(Collectors.toSet()) : null;
            device.setAccounts(accountSet);

            Set<Long> alertProfileIds = request.getAlertProfileIds();
            Set<AlertProfile> alertProfileSet = alertProfileIds != null ?
                    alertProfileIds.stream().map(alertProfileRepository::getOne).collect(Collectors.toSet()) : null;

            device.setAlertProfiles(alertProfileSet);

            //device.setVehicle(vehicleRepository.getOne(request.getVehicleId()));
            device.setProtocol(request.getProtocol());
            device.setIpAddress(request.getIpAddress());
            device.setPort(request.getPort());

            device.setTimeZoneStr(request.getTimeZoneStr());
            device.setMaxStoredDataTime(request.getMaxStoredDataTime());

            if (expiredOn != null) {
                device.setExpiredOn(expiredOn);
            }

            device.setStatus(devStatus);
            device.setSerialNumber(request.getSerivalNumber());
            device.setModelName(request.getModelName());
            device.setManufacturerName(request.getManufacturerName());
            device.setFirmwareVerison(request.getFirmwareVerison());
            device.setOriginalCountry(request.getOriginalCountry());

            device.setUpdatedBy(ApplicationContext.getCurrentUserName());
        }
        return device;
    }

    public void delete(List<Device> deviceList) {
        if (deviceList != null) {
            deviceRepository.deleteAll(deviceList);
        }
    }

    public void sendCommandToDevice(Long id, String cmdStr) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device != null) {
            CmdModel cmd = CmdModel.builder()
                    .command("DEVICE_CMD")
                    .data(cmdStr)
                    .deviceId(device.getDeviceId())
                    .remoteAddress(device.getRemoteAddress())
                    .remotePort(device.getRemotePort())
                    .build();
            try {
                // localWSService.send("{\"command\":\"CMD_TO_DEVICE\",\"data\":\"{\"device\": \"" +device.getDeviceId()+ "\", \"cmdStr\":\"" +cmdStr + "\"}\"}");
                localWSService.send(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void toggleAlertProfile(ToggleAlertProfileDTO alertProfileDTO) {
        log.info("...ToggleAlertProfile");
        Long alertProfileId = alertProfileDTO.getAlertProfileId();
        Long deviceId = alertProfileDTO.getDeviceId();
        String action = alertProfileDTO.getAction();

        AlertProfile alertProfile = alertProfileRepository.findById(alertProfileId).orElse(null);
        Assert.notNull(alertProfile, "Not found alertProfile #" + alertProfileId);

        Device device = deviceRepository.findById(deviceId).orElse(null);
        Assert.notNull(device, "Not found device #" + deviceId);
        Set<AlertProfile> alertProfileSet = device.getAlertProfiles();
        log.info("...ToggleAlertProfile {}", alertProfileSet.size());

        if (StringUtils.equalsIgnoreCase("add", action)) {
            alertProfileSet.add(alertProfile);
        } else if (StringUtils.equalsIgnoreCase("delete", action)) {
            alertProfileSet.remove(alertProfile);
        } else {
            if (alertProfileSet.contains(alertProfile)) {
                alertProfileSet.remove(alertProfile);
            } else {
                alertProfileSet.add(alertProfile);
            }
        }

        device.setAlertProfiles(alertProfileSet);
        deviceRepository.save(device);
        log.info("___ToggleAlertProfile");
    }
}
