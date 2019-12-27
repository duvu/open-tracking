package me.duvu.tracking.web.rest;

import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.DriverService;
import me.duvu.tracking.web.rest.model.request.DriverRequest;
import me.duvu.tracking.web.rest.model.response.DriverProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beou on 9/20/18 15:32
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/driver")
public class DriverController extends Vd5AdminController<DriverRequest, DriverProjection> {
    private final DriverService driverService;
    private final ProjectionFactory projectionFactory;

    @Autowired
    public DriverController(DriverService driverService, ProjectionFactory projectionFactory) {
        this.driverService = driverService;
        this.projectionFactory = projectionFactory;
    }

    @Override
    @GetMapping
    @Transactional(readOnly = true)
    public Page<DriverProjection> getAll(@RequestParam(name = "search", required = false, defaultValue = "")String search, Pageable pageable) {
        return driverService.getAll(search, pageable).map(x -> projectionFactory.createProjection(DriverProjection.class, x));
    }

    @Override
    @GetMapping("/all")
    public List<DriverProjection> getAll(@RequestParam(name = "search", required = false, defaultValue = "")String search) {
        return driverService.getAll(search).stream().map(x -> projectionFactory.createProjection(DriverProjection.class, x)).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/{id}")
    public DriverProjection getById(@PathVariable(value = "id") Long id) {
        return projectionFactory.createProjection(DriverProjection.class, driverService.getById(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverProjection create(@RequestBody @Valid DriverRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Device", result.getFieldErrors());
        return projectionFactory.createProjection(DriverProjection.class, driverService.create(request));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(value = "id") Long id, @RequestBody @Valid DriverRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Driver", result.getFieldErrors());
        driverService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        driverService.delete(id);
    }
}
