package me.duvu.tracking.repository;

import me.duvu.tracking.entities.*;
import me.duvu.tracking.entities.enumeration.AlertType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author beou on 9/12/18 10:09
 */
public class CustomAlertLogRepositoryImpl implements CustomAlertLogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AlertEventLog findLastIn(String deviceId, AlertType[] statusCodes) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AlertEventLog> query = cb.createQuery(AlertEventLog.class);
        query.distinct(true);

        Root<AlertEventLog> root = query.from(AlertEventLog.class);
        query.orderBy(cb.desc(root.get(AlertEventLog_.createdOn)));
        Predicate predicate = cb.and(
                root.get(AlertEventLog_.type).in(statusCodes),
                cb.equal(root.get(AlertEventLog_.deviceId), deviceId)
        );
        query.select(root).where(predicate);
        TypedQuery<AlertEventLog> q = entityManager.createQuery(query);
        q.setMaxResults(1);
        q.setFirstResult(0);
        List<AlertEventLog> logList = q.getResultList();
        if (logList != null && logList.size() > 0) {
            return logList.get(0);
        } else {
            return null;
        }
    }
}
