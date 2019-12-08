package me.duvu.tracking.web.rest;

import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.enumeration.AccountStatus;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.AccountService;
import me.duvu.tracking.services.SmtpPropertiesService;
import me.duvu.tracking.web.rest.model.in.SmtpPropertiesRequest;
import me.duvu.tracking.web.rest.model.out.AccountProjection;
import me.duvu.tracking.web.rest.model.in.AccountRequest;
import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.web.rest.model.out.SmtpPropertiesProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final SmtpPropertiesService smtpPropertiesService;
    private final ProjectionFactory projectionFactory;

    @Autowired
    public AccountController(AccountService accountService,
                             SmtpPropertiesService smtpPropertiesService,
                             ProjectionFactory projectionFactory) {
        this.accountService = accountService;
        this.smtpPropertiesService = smtpPropertiesService;
        this.projectionFactory = projectionFactory;
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission('#id', 'Account', 'delete') and hasAnyAuthority('SUPER', 'SYSADMIN', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    /*SmtpProperties controller*/
    @PostMapping("/add-smtp/{accountId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPER', 'SYSADMIN', 'ADMIN')")
    public SmtpPropertiesProjection addSmtpToAccount(@PathVariable Long accountId, @RequestBody @Valid SmtpPropertiesRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException("Exception while add SMTP server to account #" + accountId, result.getFieldErrors());
        }

        // prevent wrong
        request.setAccountId(accountId);

        return projectionFactory.createProjection(SmtpPropertiesProjection.class, smtpPropertiesService.create(request));
    }
}
