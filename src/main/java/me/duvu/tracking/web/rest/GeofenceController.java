package me.duvu.tracking.web.rest;

import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.GeozoneService;
import me.duvu.tracking.web.rest.model.response.GeofenceProjection;
import me.duvu.tracking.web.rest.model.request.GeofenceRequest;
import lombok.extern.slf4j.Slf4j;
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
 * @author beou on 2/19/18 17:15
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/geofence")
public class GeofenceController extends Vd5AdminController<GeofenceRequest, GeofenceProjection> {

    private final ProjectionFactory projectionFactory;
    private final GeozoneService geozoneService;

    public GeofenceController(ProjectionFactory projectionFactory,
                              GeozoneService geozoneService) {
        this.projectionFactory = projectionFactory;
        this.geozoneService = geozoneService;
    }

    @Override
    @GetMapping
    @Transactional(readOnly = true)
    public Page<GeofenceProjection> getAll(@RequestParam(name = "search", required = false, defaultValue = "") String search, Pageable pageable) {
        return geozoneService.getAll(search, pageable).map(x -> projectionFactory.createProjection(GeofenceProjection.class, x));
    }

    @Override
    @GetMapping("/all")
    @Transactional(readOnly = true)
    public List<GeofenceProjection> getAll(@RequestParam(name = "search", required = false, defaultValue = "")String search) {
        return geozoneService.getAll(search).stream().map(x -> projectionFactory.createProjection(GeofenceProjection.class, x)).collect(Collectors.toList());
    }

    @Override
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public GeofenceProjection getById(@PathVariable Long id) {
        return projectionFactory.createProjection(GeofenceProjection.class, geozoneService.getById(id));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public GeofenceProjection create(@RequestBody @Valid GeofenceRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Geofence", result.getFieldErrors());
        return projectionFactory.createProjection(GeofenceProjection.class, geozoneService.create(request));
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable Long id, @RequestBody @Valid GeofenceRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Device", result.getFieldErrors());
        geozoneService.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable Long id) {
        geozoneService.delete(id);
    }
}
