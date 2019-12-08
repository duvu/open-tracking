package me.duvu.tracking.web.rest;

import me.duvu.tracking.exception.ValidationException;
import me.duvu.tracking.services.HomeService;
import me.duvu.tracking.web.rest.model.in.RegisterRequest;
import me.duvu.tracking.web.rest.model.out.AccountProjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author beou on 1/29/19 01:26
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/home")
public class HomeController {

    private final ProjectionFactory projectionFactory;
    private final HomeService homeService;

    public HomeController(ProjectionFactory projectionFactory, HomeService homeService) {
        this.projectionFactory = projectionFactory;
        this.homeService = homeService;
    }

    @PostMapping("/register")
    public AccountProjection register(@RequestBody @Valid RegisterRequest request, BindingResult result) {
        if (result.hasErrors())
            throw new ValidationException("Account", result.getFieldErrors());
        return projectionFactory.createProjection(AccountProjection.class, homeService.register(request));
    }
}
