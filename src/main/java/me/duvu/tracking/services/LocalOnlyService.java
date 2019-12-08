package me.duvu.tracking.services;

import me.duvu.tracking.domain.EventData;
import me.duvu.tracking.repository.EventDataRepository;
import org.springframework.stereotype.Service;

/**
 * @author beou on 10/2/18 20:16
 */
@Service
public class LocalOnlyService {
    private final EventDataRepository eventDataRepository;

    public LocalOnlyService(EventDataRepository eventDataRepository) {
        this.eventDataRepository = eventDataRepository;
    }

    public EventData getLastEventData(long deviceId) {
        return eventDataRepository.getLastEventData(deviceId).get(0);
    }
}
