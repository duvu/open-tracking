package me.duvu.tracking.services;

import me.duvu.tracking.domain.EventData;
import me.duvu.tracking.repository.EventDataRepository;
import me.duvu.tracking.services.models.ParkingEvent;
import me.duvu.tracking.specification.EventDataSpecification;
import me.duvu.tracking.utils.DistanceCalculator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author beou on 4/30/18 18:14
 */
@Slf4j
@Service
@Transactional
public class DeviceReportService {

    @Value("${vd5.MINIMUM_SPEED_KPH:5}")
    private Double MINIMUM_SPEED_KPH;

    @Value("${vd5.MINIMUM_DISTANCE_METER: 30}")
    private Double MINIMUM_DISTANCE_METER;

    @Value("${vd5.MINIMUM_TIME_STOPPED: 300}")
    private Long MINIMUM_TIME_STOPPED;


    private final EventDataRepository eventDataRepository;
    private final EventDataSpecification eventDataSpecification;

    public DeviceReportService(EventDataRepository eventDataRepository,
                               EventDataSpecification eventDataSpecification) {
        this.eventDataRepository = eventDataRepository;
        this.eventDataSpecification = eventDataSpecification;
    }

    public Page<EventData> getSpeedReport(Long deviceId, Long from, Long to, Pageable pageable) {
        if (to == null || to == 0) {
            to = (new Date()).getTime();
        }

        if (from == null || from == 0) {
            from = to - (24 * 60 * 60 * 1000); // 24 hours
        }

        Specification<EventData> spec = eventDataSpecification.queryHistory(deviceId, from, to);
        return eventDataRepository.findAll(spec, pageable);
    }

    public List<EventData> getSpeedReport(Long deviceId, Long from, Long to) {
        if (to == null || to == 0) {
            to = (new Date()).getTime();
        }

        if (from == null || from == 0) {
            from = to - (24 * 60 * 60 * 1000); // 24 hours
        }

        Specification<EventData> spec = eventDataSpecification.queryHistory(deviceId, from, to);
        return eventDataRepository.findAll(spec);
    }


    public List<ParkingEvent> getParkingReport(Long deviceId, Long from, Long to) {
        if (to == null || to == 0) {
            to = (new Date()).getTime();
        }

        if (from == null || from == 0) {
            from = to - (24 * 60 * 60 * 1000); // 24 hours
        }

        Specification<EventData> spec = eventDataSpecification.queryHistoryReverse(deviceId, from, to);
        List<EventData> eventDataList = eventDataRepository.findAll(spec);

        List<ParkingEvent> parkingEventDataList = new ArrayList<>();
        int len = eventDataList.size();
        if (len > 0) {
            ParkingEvent ped = null;
            EventData prevEvdt = null;

            int i = 0;
            for (EventData evdt : eventDataList) {
                i++;
                if (prevEvdt == null) {
                } else if (i == len) {
                    ped = ParkingEvent.from(prevEvdt);

                    ped.setStartParkingTime(prevEvdt.getTimestamp());
                    ped.setEndParkingTime(evdt.getTimestamp());

                    ped.setStartParkingOdometterKM(prevEvdt.getOdometerKM());
                    ped.setEndParkingOdometterKM(evdt.getOdometerKM());

                    ped.setStoppedTime(evdt.getTimestamp() - prevEvdt.getTimestamp());

                    parkingEventDataList.add(ped);
                } else {
                    double distance = DistanceCalculator.distance(prevEvdt.getLatitude(), prevEvdt.getLongitude(), evdt.getLatitude(), evdt.getLongitude());
                    if (StringUtils.equals(prevEvdt.getAddress(), evdt.getAddress()) ||
                            distance < MINIMUM_DISTANCE_METER /*meter*/ ||
                            evdt.getSpeedKPH() < MINIMUM_SPEED_KPH)
                    {
                        continue;
                    } else {
                        long timedistance = evdt.getTimestamp() - prevEvdt.getTimestamp();
                        if (timedistance > (MINIMUM_TIME_STOPPED * 1000)) {
                            ped = ParkingEvent.from(prevEvdt);

                            ped.setStartParkingTime(prevEvdt.getTimestamp());
                            ped.setEndParkingTime(evdt.getTimestamp());

                            ped.setStartParkingOdometterKM(prevEvdt.getOdometerKM());
                            ped.setEndParkingOdometterKM(evdt.getOdometerKM());
                            ped.setStoppedTime(timedistance);

                            parkingEventDataList.add(ped);
                        }
                    }
                }

                prevEvdt = evdt;
            }
        }
        Collections.reverse(parkingEventDataList);
        return parkingEventDataList;
    }
}
