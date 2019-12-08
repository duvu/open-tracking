package me.duvu.tracking.exception;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by beou on 1/5/17.
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -8719667826135111831L;
    private List<FieldError> errorFields;

    public ValidationException(String objectName, List<FieldError> errorFields) {
        super(String.format("Validation failed for object %s.", objectName));
        this.errorFields = errorFields;
    }

    public List<FieldError> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(List<FieldError> errorFields) {
        this.errorFields = errorFields;
    }
}