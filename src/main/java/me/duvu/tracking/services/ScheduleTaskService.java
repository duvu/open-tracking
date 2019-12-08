package me.duvu.tracking.services;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

/**
 * @author beou on 10/4/19 23:57
 */

@Service
public class ScheduleTaskService {
    private final TaskScheduler scheduler;
    private Integer nextId;

    Map<Integer, ScheduledFuture<?>> jobsMap = Collections.synchronizedMap(new HashMap<>());

    public ScheduleTaskService(TaskScheduler taskScheduler) {
        nextId = 1;
        this.scheduler = taskScheduler;
    }

    public int addCronTask(String cronString, String tzString, Runnable task) {
        TimeZone tz = TimeZone.getTimeZone(tzString);
        tz = tz != null ? tz : TimeZone.getDefault();
        ScheduledFuture<?> scheduledTask = scheduler.schedule(task, new CronTrigger(cronString, tz));
        jobsMap.put(nextId, scheduledTask);
        nextId++;
        return (nextId - 1);
    }

    public void removeCronTask(int id) {
        ScheduledFuture<?> scheduledTask = jobsMap.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            jobsMap.remove(id);
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    public void refreshEvent() {
        //
    }
}
