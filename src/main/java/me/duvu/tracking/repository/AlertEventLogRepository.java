package me.duvu.tracking.repository;

import me.duvu.tracking.domain.AlertEventLog;
import org.springframework.stereotype.Repository;

/**
 * @author beou on 9/10/18 16:25
 */

@Repository
public interface AlertEventLogRepository extends Vd5CommonRepository<AlertEventLog>, CustomAlertLogRepository {
}
