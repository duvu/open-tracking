package me.duvu.tracking.repository;

import me.duvu.tracking.entities.AlertEventLog;
import me.duvu.tracking.entities.enumeration.AlertType;

/**
 * @author beou on 9/12/18 10:08
 */
public interface CustomAlertLogRepository {
    AlertEventLog findLastIn(String deviceId, AlertType[] statusCodes);
}
