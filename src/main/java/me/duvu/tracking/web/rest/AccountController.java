package me.duvu.tracking.web.rest;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.entities.Account;
import me.duvu.tracking.entities.enumeration.AccountStatus;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.AccountService;
import me.duvu.tracking.storages.StorageService;
import me.duvu.tracking.storages.UploadResult;
import me.duvu.tracking.web.rest.model.request.ChangePasswdRequest;
import me.duvu.tracking.web.rest.model.response.AccountProjection;
import me.duvu.tracking.web.rest.model.request.AccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beou on 8/1/17 03:10
 * @version 1.0
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/account")
public class AccountController extends Vd5AdminController<AccountRequest, AccountProjection> {

    private final AccountService accountService;
    private final ProjectionFactory projectionFactory;
    private final StorageService storageService;

    @Autowired
    public AccountController(AccountService accountService,
                             ProjectionFactory projectionFactory,
                             @Qualifier("storageService") StorageService storageService) {
        this.accountService = accountService;
        this.projectionFactory = projectionFactory;
        this.storageService = storageService;
    }

    @GetMapping
    public Page<AccountProjection> getAll(@RequestParam(name = "search", required = false) String search, Pageable pageable) {
        return accountService.getAll(search, pageable).map(x -> projectionFactory.createProjection(AccountProjection.class, x));
    }

    @GetMapping("/all")
    public List<AccountProjection> getAll(@RequestParam(name = "search", required = false) String search) {
        return accountService.getAll(search).stream().map(x -> projectionFactory.createProjection(AccountProjection.class, x)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Account', 'read')")
    public AccountProjection getById(@PathVariable Long id) {
        Account account = accountService.getById(id);
        if (account == null) {
            throw new AccessDeninedOrNotExisted("Not found account #id: " + id);
        }
        return projectionFactory.createProjection(AccountProjection.class, account);
    }

    @GetMapping("/me")
    public AccountProjection getMe() {
        return projectionFactory.createProjection(AccountProjection.class, accountService.getMe());
    }

    @GetMapping("/status")
    public List<AccountStatus> getStatus() {
        return Arrays.asList(AccountStatus.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER', 'SYSADMIN', 'ADMIN')")
    public AccountProjection create(@RequestBody @Valid AccountRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Account", result.getFieldErrors());
        return projectionFactory.createProjection(AccountProjection.class, accountService.create(request));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#id, 'Account', 'update')")
    public void update(@PathVariable Long id, @RequestBody @Valid AccountRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Account", result.getFieldErrors());
        accountService.update(id, request);
    }

    @PutMapping("/changePasswd")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid ChangePasswdRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Account", result.getFieldErrors());
        accountService.changePassword(request);
    }

    @PostMapping("/upload")
    public UploadResult uploadImage(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return storageService.upload(ApplicationContext.getCurrentUserName(), file);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission('#id', 'Account', 'delete') and hasAnyAuthority('SUPER', 'SYSADMIN', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }
}
