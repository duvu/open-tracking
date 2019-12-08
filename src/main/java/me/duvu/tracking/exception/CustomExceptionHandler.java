package me.duvu.tracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResource handleNoSuchElement(NoSuchElementException ex) {
        return new ErrorResource(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResource handleNoSuchElement(ObjectNotFoundException ex) {
        return new ErrorResource(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value = DeviceAlreadyExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResource handleDeviceExisted(DeviceAlreadyExistedException ex) {
        return new ErrorResource(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = EntityConstrainsExceptions.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResource handleNoSuchElement(EntityConstrainsExceptions ex) {
        return new ErrorResource(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResource handleIllegalState(IllegalStateException ex) {
        return new ErrorResource(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResource handleValidationState(ValidationException ex) {
        List<FieldErrorResource> resourceList = ex.getErrorFields().stream().map(CustomExceptionHandler::fieldErrorResourceConverter).collect(Collectors.toList());
        return new ErrorResource(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), resourceList);
    }

    @ExceptionHandler(value = ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResource handleConflict(ConflictException ex) {
        return new ErrorResource(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value = BusinessLogicException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResource handleBusinessLogicException(BusinessLogicException ex) {
        return new ErrorResource(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResource handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ErrorResource(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(value = AccessDeninedOrNotExisted.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResource accessDeniedOrNotExisted(AccessDeninedOrNotExisted ex) {
        return new ErrorResource(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }


    private static FieldErrorResource fieldErrorResourceConverter(FieldError fieldError) {
        return new FieldErrorResource(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage());
    }

}
