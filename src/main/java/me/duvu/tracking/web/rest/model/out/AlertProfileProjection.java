package me.duvu.tracking.web.rest.model.out;

import me.duvu.tracking.domain.enumeration.AlertType;
import me.duvu.tracking.domain.enumeration.DayTime;
import me.duvu.tracking.domain.enumeration.WeekDays;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

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

    Long getZoneId();

    @Value(value = "#{@geozoneService.getZoneName(target.getZoneId())}")
    String getZoneName();
    Long getParams1();
    String getParams2();

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
