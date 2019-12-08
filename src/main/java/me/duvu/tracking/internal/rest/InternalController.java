package me.duvu.tracking.internal.rest;

import me.duvu.tracking.domain.Device;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author beou on 10/8/18 12:44
 */
@RestController
@RequestMapping(value = "/internal")
public class InternalController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final DeviceService deviceService;
    private final ProjectionFactory projectionFactory;

    public InternalController(DeviceService deviceService, ProjectionFactory projectionFactory) {
        this.deviceService = deviceService;
        this.projectionFactory = projectionFactory;
    }

    @GetMapping("/device/{id}")
    public InternalDeviceProjection getById(@PathVariable(value = "id") Long id) {
        return projectionFactory.createProjection(InternalDeviceProjection.class, deviceService.getById(id));
    }

    @GetMapping("/device")
    public InternalDeviceProjection getDeviceByUniqueId(@RequestParam(value = "uniqueId") String uniqueId) {
        logger.info("GetDeviceByUniqueId +" + uniqueId);
        Device device = deviceService.findByUniqueId(uniqueId);
        if (device != null) {
            return projectionFactory.createProjection(InternalDeviceProjection.class, deviceService.findByUniqueId(uniqueId));
        } else {
            throw new AccessDeninedOrNotExisted("Device is not existed");
        }
    }
}
