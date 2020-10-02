package me.duvu.tracking.web.tracker;

import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.entities.enumeration.CellTower;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.repository.EventDataRepository;
import me.duvu.tracking.services.EventDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/tracker")
public class TrackerController {

    private final EventDataService eventDataService;
    private final EventDataRepository eventDataRepository;
    private final DeviceRepository deviceRepository;

    public TrackerController(EventDataService eventDataService,
                             EventDataRepository eventDataRepository,
                             DeviceRepository deviceRepository) {
        this.eventDataService = eventDataService;
        this.eventDataRepository = eventDataRepository;
        this.deviceRepository = deviceRepository;
    }

    @PostMapping
    public ResponseEntity<?> tracking(@RequestParam String id, @RequestParam Long timestamp,
                                   @RequestParam double lat, @RequestParam double lon,
                                   @RequestParam double speed, @RequestParam double bearing,
                                   @RequestParam double altitude, @RequestParam double accuracy,
                                   @RequestParam double batt) {

        // 1. check device exist or not
        Device device = deviceRepository.findByDeviceId(id);
        if (device == null) {
            throw new AccessDeninedOrNotExisted("Device not existed!");
        }

        EventData evdt = new EventData();
        evdt.setDevice(device);
        evdt.setLatitude(lat);
        evdt.setLongitude(lon);
        evdt.setTimestamp(timestamp);
        evdt.setSpeedKPH(speed);
        evdt.setHeading(bearing);
        evdt.setAltitude(altitude);
        evdt.setAccuracy(accuracy);
        evdt.setBatteryLevel(batt);
        if (evdt.getCellTower() == null) {
            evdt.setCellTower(new CellTower());
        }
        eventDataRepository.save(evdt);

        device.setLastLatitude(lat);
        device.setLastLongitude(lon);
        device.setLastSpeedKph(speed);
        device.setLastEventTimestamp(timestamp);
        deviceRepository.save(device);
        return ResponseEntity.ok("OK");
    }
}
