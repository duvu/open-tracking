package me.duvu.tracking.repository;

import me.duvu.tracking.entities.Device_;
import me.duvu.tracking.entities.EventData;
import me.duvu.tracking.entities.EventData_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author beou on 8/6/18 22:06
 */
public class CustomEventDataRepositoryImpl implements CustomEventDataRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<EventData> getLastEventData(Long deviceId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventData> query = cb.createQuery(EventData.class);
        query.distinct(true);
        Root<EventData> root = query.from(EventData.class);
        query.orderBy(cb.desc(root.get(EventData_.timestamp)));
        Predicate predicate = cb.equal(root.join(EventData_.device).get(Device_.id), deviceId);
        query.select(root).where(predicate);
        TypedQuery<EventData> q = entityManager.createQuery(query);
        q.setMaxResults(1);
        q.setFirstResult(0);
        return q.getResultList();
    }

    @Override
    public EventData getLastEventData(String uniqueId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventData> query = cb.createQuery(EventData.class);
        query.distinct(true);
        Root<EventData> root = query.from(EventData.class);
        query.orderBy(cb.desc(root.get(EventData_.timestamp)));

        Predicate predicate = cb.equal(root.join(EventData_.device).get(Device_.deviceId), uniqueId);
        query.select(root).where(predicate);
        TypedQuery<EventData> q = entityManager.createQuery(query);
        q.setMaxResults(1);
        q.setFirstResult(0);
        return q.getSingleResult();
    }

//    public int deleteOldEvent(int numberOfDays) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaDelete<EventData> del = cb.createCriteriaDelete(EventData.class);
//        Root<EventData> root = del.getRoot();
//        Calendar cal = Calendar.getInstance();
//
//        if (numberOfDays > 0) {
//            cal.add(Calendar.DATE, 0 - numberOfDays);
//            Date expiredDate = cal.getTime();
//            Predicate predicate = cb.lessThan(root.get(EventData_.createdOn), expiredDate);
//            del.where(predicate);
//
//            return entityManager.createQuery(del).executeUpdate();
//        } else {
//            Expression<Date> dateExpression = cb.parameter(Date.class);
//            cb.a
//            //cb.diff(cb.currentDate(), root.join(EventData_.device).get(Device_.maxStoredDataTime))
//            cb.diff(cb.currentTimestamp(), 3)
//            Predicate predicate = cb.lessThan(root.get(EventData_.createdOn), dateExpression);// root.join(EventData_.device).get(Device_.maxStoredDataTime));
//            return 0;
//        }
//    }
}
