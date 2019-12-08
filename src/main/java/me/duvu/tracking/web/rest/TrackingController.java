package me.duvu.tracking.web.rest;

import me.duvu.tracking.services.EventDataService;
import me.duvu.tracking.web.rest.model.out.TrackProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/track")
public class TrackingController {

    private final ProjectionFactory projectionFactory;
    private final EventDataService eventDataService;

    public TrackingController(ProjectionFactory projectionFactory, EventDataService eventDataService) {
        this.projectionFactory = projectionFactory;
        this.eventDataService = eventDataService;
    }

    @GetMapping("/live")
    public List<TrackProjection> getLive() {
        return eventDataService.getLive().stream()
                .map(x -> {
                    if (x != null) {
                     return projectionFactory.createProjection(TrackProjection.class, x);
                    } else {
                        return null;
                    }
                }).collect(Collectors.toList());
    }

    @GetMapping("/history/{deviceId}")
    public List<TrackProjection> getHistory(@RequestParam(name = "timeFrom", required = false, defaultValue = "0") Long timeFrom,
                                            @RequestParam(name = "timeTo", required = false, defaultValue = "0") Long timeTo,
                                            @PathVariable(name = "deviceId") Long deviceId) {
        return eventDataService.getHistory(deviceId, timeFrom, timeTo).stream()
                .map(x -> projectionFactory.createProjection(TrackProjection.class, x)).collect(Collectors.toList());
    }
}
