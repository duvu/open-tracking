package me.duvu.tracking.web.rest;

import me.duvu.tracking.entities.Account;
import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.external.email.MailService;
import me.duvu.tracking.services.HomeService;
import me.duvu.tracking.web.rest.model.request.RegisterRequest;
import me.duvu.tracking.web.rest.model.response.AccountProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * @author beou on 1/29/19 01:26
 */
@Slf4j
@RestController
@RequestMapping(value = "/api")
public class HomeController {

    private final ProjectionFactory projectionFactory;
    private final HomeService homeService;
    private final MailService mailService;

    public HomeController(ProjectionFactory projectionFactory, HomeService homeService, MailService mailService) {
        this.projectionFactory = projectionFactory;
        this.homeService = homeService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public AccountProjection register(@RequestBody @Valid RegisterRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Account", result.getFieldErrors());

        Account newAccount = homeService.register(request);
        mailService.sendActivationEmail(newAccount);
        return projectionFactory.createProjection(AccountProjection.class, newAccount);
    }


    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public AccountProjection activateAccount(@RequestParam(value = "key") String key) {
        Account user = homeService.activateRegistration(key);
        return projectionFactory.createProjection(AccountProjection.class, user);
    }

}
