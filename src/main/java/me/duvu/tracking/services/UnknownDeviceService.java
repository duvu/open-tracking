package me.duvu.tracking.services;

import me.duvu.tracking.domain.Device;
import me.duvu.tracking.domain.UnknownDevice;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.repository.UnknownDeviceRepository;
import me.duvu.tracking.specification.UnknownDeviceSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beou on 10/22/18 22:58
 */

@Service
public class UnknownDeviceService {
    private final UnknownDeviceRepository unknownDeviceRepository;
    private final UnknownDeviceSpecification specification;

    private final DeviceRepository deviceRepository;

    public UnknownDeviceService(UnknownDeviceRepository unknownDeviceRepository,
                                UnknownDeviceSpecification specification,
                                DeviceRepository deviceRepository) {
        this.unknownDeviceRepository = unknownDeviceRepository;
        this.specification = specification;
        this.deviceRepository = deviceRepository;
    }

    public Page<UnknownDevice> getAll(String search, Pageable pageable) {
        Specification<UnknownDevice> spec = specification.search(search);
        return unknownDeviceRepository.findAll(spec, pageable);
    }

    public int addAllDevice() {
        List<UnknownDevice> unknownDeviceList = unknownDeviceRepository.findAll();
        List<Device> deviceList = new ArrayList<>();
        for(UnknownDevice ud : unknownDeviceList) {
            Device device = new Device();
            device.setDeviceId(ud.getUniqueId());
            device.setPort(ud.getPort());
            deviceList.add(device);
        }

        deviceRepository.saveAll(deviceList);
        unknownDeviceRepository.deleteAll(unknownDeviceList);
        return deviceList.size();
    }

    public void addAllDevice(Long[] selections) {
        List<UnknownDevice> unknownDeviceList = Arrays.stream(selections).map(unknownDeviceRepository::getOne).collect(Collectors.toList());
        List<Device> deviceList = unknownDeviceList.stream().map(x -> {
            Device device = new Device();
            device.setDeviceId(x.getUniqueId());
            device.setPort(x.getPort());
            return device;
        }).collect(Collectors.toList());
        unknownDeviceRepository.deleteAll(unknownDeviceList);
        deviceRepository.saveAll(deviceList);
    }

    public UnknownDevice create(String uniqueId, String remoteIpAddress, int port) {
        UnknownDevice ud = new UnknownDevice();
        ud.setUniqueId(uniqueId);
        ud.setRemoteIpAddress(remoteIpAddress);
        ud.setPort(port);

        return unknownDeviceRepository.save(ud);
    }

    public void delete(Long id) {
        Specification<UnknownDevice> spec = specification.findOne(id);
        UnknownDevice ud = unknownDeviceRepository.findOne(spec).orElse(null);
        if (ud != null) {
            unknownDeviceRepository.delete(ud);
        } else {
            throw new AccessDeninedOrNotExisted("Not able to find UnknownDevice Object");
        }
    }

    public void delete(String uniqueId) {
        Specification<UnknownDevice> spec = specification.search(uniqueId);
        UnknownDevice ud = unknownDeviceRepository.findOne(spec).orElse(null);
        if (ud != null) {
            unknownDeviceRepository.delete(ud);
        } else {
            throw new AccessDeninedOrNotExisted("Not able to find UnknownDevice Object");
        }
    }

    public void create(UnknownDevice data) {
        String uniqueId = data.getUniqueId();
        if (StringUtils.isNotBlank(uniqueId)) {
            UnknownDevice ud = unknownDeviceRepository.getByUniqueId(uniqueId);
            if (ud == null) {
                unknownDeviceRepository.save(data);
            }
        }
    }

    public void deleteAllUknDevice() {
        unknownDeviceRepository.deleteAll();
    }


}
