package me.duvu.tracking.specification;

import me.duvu.tracking.entities.Device_;
import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.entities.EventData_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class EventDataSpecification {
    public Specification<EventData> queryLive(Long deviceId) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get(EventData_.timestamp)));
            query.distinct(true);
            return cb.equal(root.join(EventData_.device).get(Device_.id), deviceId);
        };
    }

    public Specification<EventData> queryHistory(Long deviceId, Long timeFrom, Long timeTo) {
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get(EventData_.timestamp)));
            query.distinct(true);

            return cb.and(cb.equal(root.join(EventData_.device).get(Device_.id), deviceId),
                    cb.between(root.get(EventData_.timestamp), timeFrom, timeTo));
        };
    }

    public Specification<EventData> queryHistoryReverse(Long deviceId, Long timeFrom, Long timeTo) {
        return (root, query, cb) -> {
            query.orderBy(cb.asc(root.get(EventData_.timestamp)));
            query.distinct(true);

            return cb.and(cb.equal(root.join(EventData_.device).get(Device_.id), deviceId),
                    cb.between(root.get(EventData_.timestamp), timeFrom, timeTo));
        };
    }

    public Specification<EventData> queryAllByDeviceId(Long deviceId) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.join(EventData_.device).get(Device_.id), deviceId);
        };
    }
}
