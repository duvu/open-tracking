package me.duvu.tracking.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author beou on 10/15/17 06:06
 */
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#account.name == authentication.name")
public @interface AccountPermission {
}
