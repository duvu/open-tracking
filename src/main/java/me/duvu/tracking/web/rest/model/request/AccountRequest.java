package me.duvu.tracking.web.rest.model.request;

import lombok.Data;
import me.duvu.tracking.entities.MailProperties;
import me.duvu.tracking.entities.enumeration.AccountStatus;
import me.duvu.tracking.entities.enumeration.Roles;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * @author beou on 9/21/17 03:53
 * @version 1.0
 */
@Data
public class AccountRequest implements Serializable {
    private static final long serialVersionUID = -591813592141537331L;

    @NotNull
    @Size(max = 32)
    private String accountId;

    private String password;

    @Size(max = 25)
    private String firstName;

    @Size(max = 25)
    private String lastName;

    private String status;

    private String timeZoneStr;

    private String language;

    private Roles privilege;

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

    private MailProperties mailProperties;
}
