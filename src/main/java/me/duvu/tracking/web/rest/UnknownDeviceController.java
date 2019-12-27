package me.duvu.tracking.web.rest;

import me.duvu.tracking.domain.UnknownDevice;
import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.UnknownDeviceService;
import me.duvu.tracking.web.rest.model.request.SelectionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author beou on 10/24/18 03:46
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/unknowndevice")
public class UnknownDeviceController {

    private final UnknownDeviceService unknownDeviceService;

    public UnknownDeviceController(UnknownDeviceService unknownDeviceService) {
        this.unknownDeviceService = unknownDeviceService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SUPER')")
    public Page<UnknownDevice> getAll(@RequestParam(name = "search", required = false, defaultValue = "") String search, Pageable pageable) {
        return unknownDeviceService.getAll(search, pageable);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SUPER')")
    public void addSelected(@RequestBody @Valid SelectionModel request, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException("UnknownDevice", result.getFieldErrors());
        }
        unknownDeviceService.addAllDevice(request.getSelections());
    }

    @PutMapping("/all")
    @PreAuthorize("hasAuthority('SUPER')")
    public int addAll() {
        return unknownDeviceService.addAllDevice();
    }

    @DeleteMapping("/all")
    @PreAuthorize("hasAuthority('SUPER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteALl() {
        this.unknownDeviceService.deleteAllUknDevice();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SUPER')")
    public void delete(@PathVariable(value = "id") Long id) {
        unknownDeviceService.delete(id);
    }

}
