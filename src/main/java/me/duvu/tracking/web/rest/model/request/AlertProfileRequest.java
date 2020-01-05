package me.duvu.tracking.web.rest.model.request;

import me.duvu.tracking.domain.enumeration.AlertCatalog;
import me.duvu.tracking.domain.enumeration.AlertType;
import me.duvu.tracking.domain.enumeration.DayTime;
import me.duvu.tracking.domain.enumeration.WeekDays;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author beou on 8/4/18 16:33
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertProfileRequest implements Serializable {

    private static final long serialVersionUID = 5071997159504587343L;

    private String name;
    private String description;
    private AlertCatalog catalog;
    private AlertType type;
    private boolean active;
    private Double speedKph;
    private Long zoneId;
    private Long params1;
    private String params2;
    private WeekDays weekDays;
    private DayTime dayTime;

    private boolean isAlertEmail;
    private boolean isAlertSms;
    private boolean isAlertApp;
    private String cannedAction; // use to call to external URL

    private String subject;
    private String text;
    private String templateId;
}
