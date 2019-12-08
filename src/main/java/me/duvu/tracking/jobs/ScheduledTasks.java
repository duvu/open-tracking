package me.duvu.tracking.jobs;

import me.duvu.tracking.domain.Device;
import me.duvu.tracking.domain.enumeration.DeviceStatus;
import me.duvu.tracking.services.DeviceService;
import me.duvu.tracking.services.EventDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author beou on 1/29/18 18:04
 */

@Slf4j
@Component
public class ScheduledTasks {

    private final DeviceService deviceService;
    private final EventDataService eventDataService;

    public ScheduledTasks(DeviceService deviceService,
                          EventDataService eventDataService) {
        this.deviceService = deviceService;
        this.eventDataService = eventDataService;
    }

    // @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    @Scheduled(cron="0 2 * * * ?", zone="Asia/Saigon") //every day at 2AM
    public void checkExpired() {
        List<Device> expiredToday = deviceService.getAllExpiredToday();
        for (Device device : expiredToday) {
            device.setStatus(DeviceStatus.EXPIRED);
            deviceService.save(device);
        }
    }

    // @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    // @Scheduled(cron="30 2 * * * ?", zone="Asia/Saigon") //every hour at minute of 2:30
    // @Scheduled(cron="0 0 2 * * *", zone="Asia/Saigon") //daily at hour of 2:0:0
    // @Scheduled(cron="0 0 8-10 * * *", zone="Asia/Saigon") //8, 9 and 10 o'clock of every day.
    @Scheduled(cron="0 0 8 * * *", zone="Asia/Saigon") //8 o'clock of every day.
    public void deleteOldData() {
        eventDataService.deleteOldEventDate(3);
    }
}
