package me.duvu.tracking.services;

import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.repository.EventDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author beou on 12/21/17 01:31
 */
@Service
public class DashboardService {

    private final EventDataRepository eventDataRepository;

    public DashboardService(EventDataRepository eventDataRepository) {
        this.eventDataRepository = eventDataRepository;
    }

    public List<EventData> getHistorical(String deviceId) {
        return null;
    }
}
