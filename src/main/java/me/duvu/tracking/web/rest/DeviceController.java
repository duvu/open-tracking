package me.duvu.tracking.web.rest;

import me.duvu.tracking.domain.enumeration.DeviceStatus;
import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.DeviceService;
import me.duvu.tracking.web.rest.model.request.DeviceGroupModel;
import me.duvu.tracking.web.rest.model.response.DeviceGroupProjection;
import me.duvu.tracking.web.rest.model.response.DeviceProjection;
import me.duvu.tracking.web.rest.model.request.DeviceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beou on 8/1/17 03:10
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/device")
public class DeviceController extends Vd5AdminController<DeviceRequest, DeviceProjection> {

    private final DeviceService deviceService;
    private final ProjectionFactory projectionFactory;

    public DeviceController(DeviceService deviceService,
                            ProjectionFactory projectionFactory) {
        this.deviceService = deviceService;
        this.projectionFactory = projectionFactory;
    }

    @Override
    @GetMapping
    @Transactional(readOnly = true)
    public Page<DeviceProjection> getAll(@RequestParam(name = "search", required = false, defaultValue = "") String search, Pageable pageable) {
        return deviceService.getAll(search, pageable).map(x -> projectionFactory.createProjection(DeviceProjection.class, x));
    }

    @Override
    @GetMapping("/all")
    public List<DeviceProjection> getAll(@RequestParam(name = "search", required = false, defaultValue = "")String search) {
        return deviceService.getAll(search).stream().map(x -> projectionFactory.createProjection(DeviceProjection.class, x)).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/{id}")
    public DeviceProjection getById(@PathVariable(value = "id") Long id) {
        return projectionFactory.createProjection(DeviceProjection.class, deviceService.getById(id));
    }

    //-- send command to device
    @PostMapping("/{id}/cmd")
    public void sendCommandToDevice(@PathVariable(value = "id") Long id, @RequestBody String cmdStr) {
        deviceService.sendCommandToDevice(id, cmdStr);
    }


    @GetMapping("/status")
    public List<DeviceStatus> getStatus() {
        return Arrays.asList(DeviceStatus.getAll());
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER', 'SYSADMIN', 'ADMIN', 'MODERATOR')")
    public DeviceProjection create(@RequestBody @Valid DeviceRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Device", result.getFieldErrors());
        return projectionFactory.createProjection(DeviceProjection.class, deviceService.create(request));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(value = "id") Long id, @RequestBody @Valid DeviceRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Device", result.getFieldErrors());
        deviceService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPER', 'SYSADMIN', 'ADMIN', 'MODERATOR')")
    public void delete(@PathVariable(value = "id") Long id) {
        deviceService.delete(id);
    }


    //-- group api
    //-- get all group
    @GetMapping("/group")
    public Page<DeviceGroupProjection> getAllGroups(@RequestParam(name = "search", required = false, defaultValue = "") String search, Pageable pageable) {
        //return deviceService.getAllGroups(search, pageable)
        return null;
    }

    @PostMapping("/add-to-group")
    public void addDeviceToGroup(@RequestBody @Valid DeviceGroupModel deviceGroupModel) {

    }

    @PostMapping("/remove-from-group")
    public void removeDeviceFromGroup(@RequestBody @Valid DeviceGroupModel deviceGroupModel) {

    }


}
