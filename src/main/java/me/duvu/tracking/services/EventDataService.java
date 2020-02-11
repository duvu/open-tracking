package me.duvu.tracking.services;

import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.repository.EventDataRepository;
import me.duvu.tracking.specification.DeviceSpecification;
import me.duvu.tracking.specification.EventDataSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventDataService {

    private final EventDataRepository eventDataRepository;
    private final EventDataSpecification eventDataSpecification;

    private final DeviceRepository deviceRepository;
    private final DeviceSpecification deviceSpecification;

    public EventDataService(EventDataRepository eventDataRepository,
                            EventDataSpecification eventDataSpecification,
                            DeviceRepository deviceRepository1,
                            DeviceSpecification deviceSpecification1) {
        this.eventDataRepository = eventDataRepository;
        this.eventDataSpecification = eventDataSpecification;
        this.deviceRepository = deviceRepository1;
        this.deviceSpecification = deviceSpecification1;
    }

    public List<EventData> getLive() {
        Specification<Device> specification1 = deviceSpecification.search(null);
        List<Device> deviceList = deviceRepository.findAll(specification1);
        return deviceList.stream().map(x -> {
            List<EventData> l = eventDataRepository.getLastEventData(x.getId());
            if (l != null && l.size() > 0) {
                return l.get(0);
            } else {
                return null;
            }
        }).collect(Collectors.toList());
    }

    public List<EventData> getHistory(Long deviceId, Long timeFrom, Long timeTo) {
        if (timeTo <= 0) timeTo = System.currentTimeMillis();
        if (timeFrom <= 0) timeFrom = timeTo - 6 * 60 * 60 * 1000;

        Specification<EventData> specification = eventDataSpecification.queryHistory(deviceId, timeFrom, timeTo);
        return eventDataRepository.findAll(specification);
    }

    public List<EventData> getLastEventForDevices(final List<Device> deviceList) {
        List<Long> deviceIds = deviceList.stream().map(Device::getId).collect(Collectors.toList());
        return getLastEventForDeviceIds(deviceIds);
    }

    public List<EventData> getLastEventForDevices(final Page<Device> deviceList) {
        List<Long> deviceIds = deviceList.stream().map(Device::getId).collect(Collectors.toList());
        return getLastEventForDeviceIds(deviceIds);
    }

    public List<EventData> getLastEventForDeviceIds(final List<Long> deviceIds) {

        List<EventData> dataList = new ArrayList<>();
        for(Long d : deviceIds) {
            Specification<EventData> specification = eventDataSpecification.queryLive(d);
            List<EventData> eventDataList = eventDataRepository.findAll(specification, 0, 1);
            if (eventDataList != null && eventDataList.size() > 0) {
                dataList.add(eventDataList.get(0));
            }
        }
        return dataList;
    }

    @Transactional
    public void deleteOldEventDate(int numberOfDays) {
        log.info("Cleanup database!");
        if (numberOfDays > 0) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 0 - numberOfDays);
            Date expiredDate = cal.getTime();
            eventDataRepository.deleteByCreatedOnBefore(expiredDate);
        } else {

        }
    }

    public Long countAll() {
        return eventDataRepository.count(); //count all device
    }

    @Transactional
    public void purgeByDevice(Device device) {
        if (device != null) {
            Specification<EventData> spec = eventDataSpecification.queryAllByDeviceId(device.getId());
            List<EventData> allEventByDeviceId = eventDataRepository.findAll(spec);
            eventDataRepository.deleteAll(allEventByDeviceId);
        }
    }
}
