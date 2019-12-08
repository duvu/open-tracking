package me.duvu.tracking.web.rest.model.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author beou on 1/29/19 01:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {
    private static final long serialVersionUID = -605763715723852517L;

    @NotNull
    @Size(max = 32)
    private String accountId;

    private String password;
    private String rePassword;

    @Size(max = 25)
    private String firstName;

    @Size(max = 25)
    private String lastName;


    private String timeZoneStr;

    private String language;


    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 255)
    private String photoUrl;

    private String firstPageUrl;

    @NotNull
    @Size(max = 128)
    private String emailAddress;

    @Size(max = 128)
    private String addressLine1;

    @Size(max = 128)
    private String addressLine2;

    @Size(max = 512)
    private String notes;
}
