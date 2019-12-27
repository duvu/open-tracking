package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author beou on 9/9/18 15:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverRequest implements Serializable {

    private static final long serialVersionUID = -7652959612174782650L;

    @Size(max = 25)
    private String firstName;

    @Size(max = 25)
    private String lastName;

    private Date dateOfBirth;

    private String typeOfVehicleExperienced;

    private String driverLicenseNumber;

    private String driverLicenseType;

    private String driverLicenseTypeDescription;

    private Date driverLicenseIssueDate;

    private Date driverLicenseExpiredDate;
}
