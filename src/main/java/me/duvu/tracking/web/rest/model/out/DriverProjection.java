package me.duvu.tracking.web.rest.model.out;

import java.util.Date;
import java.util.List;

/**
 * @author beou on 9/9/18 15:23
 */
public interface DriverProjection {
    Long getId();
    String getFirstName();
    String getLastName();

    Date getDateOfBirth();
    int getDriverExperiencesMonths();
    String getTypeOfVehicleExperienced();
    String getDriverLicenseNumber();
    String getDriverLicenseType();
    String getDriverLicenseTypeDescription();
    Date getDriverLicenseIssueDate();
    String getCreatedBy();
    String getUpdatedBy();
    Date getCreatedOn();
    Date getUpdatedOn();
}
