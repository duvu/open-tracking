package me.duvu.tracking.internal;


import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.config.StatusCodes;
import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.entities.enumeration.CellTower;
import me.duvu.tracking.internal.model.Position;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.repository.EventDataRepository;
import me.duvu.tracking.utils.DistanceCalculator;
import me.duvu.tracking.utils.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author beou on 10/9/18 02:09
 */

@Slf4j
@Service
public class PositionService {
    private final EventDataRepository eventDataRepository;
    private final DeviceRepository deviceRepository;

    @Value("${vd5.MINIMUM_SPEED_KPH:5}")
    private Double MINIMUM_SPEED_KPH;

    @Value("${vd5.MINIMUM_DISTANCE_METER: 30}")
    private Double MINIMUM_DISTANCE_METER;


    private final List<EventData> eventDataList = Collections.synchronizedList(new ArrayList<>());
    private final Map<Long, Device> deviceMap = Collections.synchronizedMap(new HashMap<>());

    public PositionService(EventDataRepository eventDataRepository, DeviceRepository deviceRepository) {
        this.eventDataRepository = eventDataRepository;
        this.deviceRepository = deviceRepository;
    }

    public void add(Position position) {
        if (position != null && position.getDeviceId() > 0) {
            EventData eventData = new EventData();
            long deviceId = position.getDeviceId();
            //Device device = deviceRepository.findById(deviceId).orElse(null);
            Device device = loadDevice(deviceId);

            if (device != null) {
                String lastAddress = device.getLastAddress();
                Date stayedTime = device.getStayedTime();

                double lastLatitude = device.getLastLatitude() != null ? device.getLastLatitude() : 0;
                double lastLongitude = device.getLastLongitude() != null ? device.getLastLongitude() : 0;

                double distance = DistanceCalculator.distance(lastLatitude, lastLongitude, position.getLatitude(), position.getLongitude());
                if (stayedTime == null ||
                        (!StringUtils.equals(lastAddress, position.getAddress()) &&
                                position.getSpeed() >= MINIMUM_SPEED_KPH) && distance >= MINIMUM_DISTANCE_METER) {
                    device.setStayedTime(position.getDeviceTime());
                }
                //-- update device ---
                device.setLastAddress(position.getAddress());
                device.setLastLongitude(position.getLongitude());
                device.setLastLatitude(position.getLatitude());
                device.setLastEventTime(position.getDeviceTime());
                device.setLastSpeedKph(position.getSpeed());
                device.setRemoteAddress(position.getString(Position.KEY_REMOTE_ADDRESS));
                device.setRemotePort(position.getInteger(Position.KEY_REMOTE_PORT));

                //deviceRepository.save(device);
                deviceMap.put(deviceId, device);

                //--
                Date fixTime = position.getFixTime();
                long fixTimestamp = fixTime != null ? fixTime.getTime() : new Date().getTime();



                eventData.setDevice(device);
                eventData.setTimestamp(fixTimestamp);

                String alarm = position.getString(Position.KEY_ALARM);

                if (position.getSpeed() > 0) {
                    eventData.setStatus(StatusCodes.STATUS_MOTION_EN_ROUTE);
                } else {
                    eventData.setStatus(StatusCodes.STATUS_LOCATION);
                }
                eventData.setOdometerKM(position.getDouble(Position.KEY_ODOMETER));
                eventData.setHeading(position.getCourse());
                eventData.setAltitude(position.getAltitude());
                eventData.setLongitude(position.getLongitude());
                eventData.setLatitude(position.getLatitude());
                eventData.setSpeedKPH(position.getSpeed());
                eventData.setAddress(position.getAddress());

                //--//--
                eventData.setRemoteAddress(position.getString(Position.KEY_REMOTE_ADDRESS));
                eventData.setRemotePort(position.getInteger(Position.KEY_REMOTE_PORT));

                eventData.setBatteryLevel(position.getDouble(Position.KEY_BATTERY_LEVEL));
                eventData.setFuelLevel(position.getDouble(Position.KEY_FUEL_LEVEL));

                if (eventData.getCellTower() == null) {
                    eventData.setCellTower(new CellTower());
                }

                eventDataList.add(eventData);
            }
        }
    }

    private Device loadDevice(Long deviceId) {
        if (deviceMap.size() == 0 || deviceMap.get(deviceId) == null) {
            Device dev = deviceRepository.findById(deviceId).orElse(null);
            deviceMap.put(deviceId, dev);
            return dev;
        } else {
            return deviceMap.get(deviceId);
        }
    }

    @Scheduled(fixedRate = 10000, initialDelay = 5000)
    public void updateDeviceTable() {
        synchronized (deviceMap) {
            List<Device> updatedDeviceList = new ArrayList<>(deviceMap.values());
            if (updatedDeviceList.size() > 0) {
                try {
                    deviceRepository.saveAll(updatedDeviceList);
                } finally {
                    deviceMap.clear();
                }

            }
        }
    }

    @Scheduled(fixedRate = 10000, initialDelay = 10000)
    public void saveEventDataToDB() {
        synchronized (eventDataList) {
            if (eventDataList.size() > 0) {
                try {
                    Log.info("[>_] saving " + eventDataList.size() + " items");
                    eventDataRepository.saveAll(eventDataList);
                } finally {
                    eventDataList.clear();
                }
            }
        }
    }
}
