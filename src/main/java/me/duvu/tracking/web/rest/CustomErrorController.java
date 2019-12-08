package me.duvu.tracking.web.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author beou on 8/9/18 15:31
 */
@RestController
public class CustomErrorController implements ErrorController {


    private String error() {
        return "error";
    }
    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }
}
