package me.duvu.tracking.jobs;

import me.duvu.tracking.services.DeviceService;
import org.springframework.stereotype.Component;

/**
 * @author beou on 7/25/19 00:11
 */

@Component
public class WeeklyTask {
    private final DeviceService deviceService;

    public WeeklyTask(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
