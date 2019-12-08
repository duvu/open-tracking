package me.duvu.tracking.web.rest;

import me.duvu.tracking.domain.AlertEventLog;
import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.AlertProfileService;
import me.duvu.tracking.web.rest.model.in.AlertProfileRequest;
import me.duvu.tracking.web.rest.model.out.AlertProfileProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beou on 8/5/18 11:44
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/alert")
public class AlertController extends Vd5AdminController<AlertProfileRequest, AlertProfileProjection> {

    private final AlertProfileService alertProfileService;
    private final ProjectionFactory projectionFactory;


    public AlertController(AlertProfileService alertProfileService,
                           ProjectionFactory projectionFactory) {
        this.alertProfileService = alertProfileService;
        this.projectionFactory = projectionFactory;
    }

    @GetMapping
    @Override
    public Page<AlertProfileProjection> getAll(@RequestParam(name = "search", required = false) String search, Pageable pageable) {
        return alertProfileService.getAll(search, pageable).map(x -> projectionFactory.createProjection(AlertProfileProjection.class, x));
    }

    @GetMapping("/all")
    @Override
    public List<AlertProfileProjection> getAll(@RequestParam(name = "search", required = false) String search) {
        return alertProfileService.getAll(search).stream().map(x -> projectionFactory.createProjection(AlertProfileProjection.class, x)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Override
    public AlertProfileProjection getById(@PathVariable Long id) {
        return projectionFactory.createProjection(AlertProfileProjection.class, alertProfileService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public AlertProfileProjection create(@RequestBody @Valid AlertProfileRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException("Account", result.getFieldErrors());
        }
        return projectionFactory.createProjection(AlertProfileProjection.class, alertProfileService.create(request));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@PathVariable Long id, @RequestBody @Valid AlertProfileRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException("AlertProfile", result.getFieldErrors());
        }
        alertProfileService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    @PreAuthorize("hasAnyAuthority('SUPER')")
    public void delete(@PathVariable Long id) {
        alertProfileService.delete(id);
    }

    //-- alert event log

    @GetMapping("/r/{id}")
    public Page<AlertEventLog> getAllAlertEventLogs(@PathVariable Long id,
                                                    @RequestParam(name = "from", required = false) Long from,
                                                    @RequestParam(name = "to", required = false) Long to,
                                                    Pageable pageable) {
        return alertProfileService.getAllAlertEventLog(id, from, to, pageable);
    }

}
