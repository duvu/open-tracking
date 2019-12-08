package me.duvu.tracking.repository;

import me.duvu.tracking.domain.EventData;

import java.util.List;

/**
 * @author beou on 8/6/18 22:00
 */
public interface CustomEventDataRepository {
    List<EventData> getLastEventData(Long deviceId);
    EventData getLastEventData(String deviceId);
}
