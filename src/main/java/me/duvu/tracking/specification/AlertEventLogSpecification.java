package me.duvu.tracking.specification;

import me.duvu.tracking.entities.AlertEventLog;
import me.duvu.tracking.entities.AlertEventLog_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author beou on 8/29/19 22:29
 */
@Component
public class AlertEventLogSpecification extends AbstractSpecification<AlertEventLog> {

    @Override
    public Specification<AlertEventLog> search(String search) {
        return null;
    }

    public Specification<AlertEventLog> searchForDeviceBetween(Long deviceId, Long from, Long to) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get(AlertEventLog_.timestamp)));
            query.distinct(true);

            return cb.and(cb.equal(root.get(AlertEventLog_.deviceId), deviceId),
                    cb.between(root.get(AlertEventLog_.timestamp), from, to));
        };
    }
}
