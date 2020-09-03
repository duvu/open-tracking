package me.duvu.tracking.web.rest.model.response;

import me.duvu.tracking.entities.enumeration.AlertCatalog;
import me.duvu.tracking.entities.enumeration.AlertType;
import me.duvu.tracking.entities.enumeration.DayTime;
import me.duvu.tracking.entities.enumeration.WeekDays;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * @author beou on 8/5/18 11:42
 */
public interface AlertProfileProjection {
    Long getId();
    String getName();
    String getDescription();
    AlertType getType();
    boolean isActive();
    Double getSpeedKph();

    @Value(value = "#{target.getZone() != null ? target.getZone().getName() : null }")
    String getZoneName();

    @Value(value = "#{target.getZone() != null ? target.getZone().getId() : null }")
    Long getZoneId();

    Long getParams1();
    String getParams2();

    AlertCatalog getCatalog();
    WeekDays getWeekDays();
    DayTime getDayTime();

    boolean isAlertEmail();
    boolean isAlertSms();
    boolean isAlertApp();
    String getCannedAction();

    String getSubject();
    String getText();
    String getTemplateId();

    String getCreatedBy();
    String getUpdatedBy();
    Date getCreatedOn();
    Date getUpdatedOn();
}
