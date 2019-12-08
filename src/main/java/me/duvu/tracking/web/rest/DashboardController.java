package me.duvu.tracking.web.rest;

import me.duvu.tracking.services.DashboardService;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beou on 12/21/17 01:56
 */
@RestController
@RequestMapping(value = "/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;
    private final ProjectionFactory projectionFactory;

    public DashboardController(DashboardService dashboardService, ProjectionFactory projectionFactory) {
        this.dashboardService = dashboardService;
        this.projectionFactory = projectionFactory;
    }
}
