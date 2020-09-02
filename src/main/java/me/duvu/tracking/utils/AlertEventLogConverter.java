package me.duvu.tracking.utils;

import me.duvu.tracking.entities.AlertEventLog;
import me.duvu.tracking.entities.AlertProfile;

/**
 * @author beou on 8/29/19 16:37
 */
public class AlertEventLogConverter {

    public static AlertEventLog from(AlertProfile alert) {
        return AlertEventLog.builder()
                .alertName(alert.getName())
                .alertDescription(alert.getDescription())
                .accountId(alert.getAccount().getId())
                .type(alert.getType())
                .speedKph(alert.getSpeedKph())
                .zone(alert.getZone())
                .alertEmail(alert.isAlertEmail())
                .alertSms(alert.isAlertSms())
                .alertApp(alert.isAlertApp())
                .cannedAction(alert.getCannedAction())
                .subject(alert.getSubject())
                .text(alert.getText())
                .templateId(alert.getTemplateId())
                .receivers(alert.getAccount().getEmailAddress())
                .build();
    }
}
