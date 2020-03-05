package me.duvu.tracking.web.rest.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ChangePasswdRequest implements Serializable {
    private static final long serialVersionUID = 7852340431469964131L;

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 6, max = 64)
    private String password;

    @NotNull
    @Size(min = 6, max = 64)
    private String rePassword;
}
