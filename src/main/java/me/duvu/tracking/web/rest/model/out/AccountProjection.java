package me.duvu.tracking.web.rest.model.out;

import me.duvu.tracking.domain.enumeration.Roles;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author beou on 9/21/17 03:51
 * @version 1.0
 */
public interface AccountProjection {
    Long getId();
    String getAccountId();

    String getFirstName();
    String getLastName();
    String getStatus();

    Roles getPrivilege();

    @Value(value = "#{target.getTimeZoneStr() == null ? 'UTC' : target.getTimeZoneStr() }")
    String getTimeZone();

    @Value(value = "#{target.getLanguage() == null ? 'EN' : target.getLanguage() }")
    String getLanguage();

    String getPhoneNumber();
    String getPhotoUrl();
    String getFirstPageUrl();

    String getEmailAddress();
    String getAddressLine1();
    String getAddressLine2();

    String getNotes();

    String getCreatedBy();
    String getUpdatedBy();
    Date getCreatedOn();
    Date getUpdatedOn();


}
