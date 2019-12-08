package me.duvu.tracking.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

public class ExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements ExtendedRepository<T, ID> {

    private final EntityManager entityManager;

    public ExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<T> findAll(Specification<T> spec, int offset, int maxResults) {
        return findAll(spec, offset, maxResults, Sort.unsorted());
    }

    @Override
    @Transactional
    public List<T> findAll(Specification<T> spec, int offset, int maxResults, Sort sort) {
        TypedQuery<T> query = getQuery(spec, sort);

        if (offset < 0) {
            throw new IllegalArgumentException("Offset must not be less than zero!");
        }
        if (maxResults < 1) {
            throw new IllegalArgumentException("Max results must not be less than one!");
        }

        query.setFirstResult(offset);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<T> searchByField(String attr, String text) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getDomainClass());


        Root<T> root = query.from(getDomainClass());
        query.select(root).where(builder.like(root.<String>get(attr), "%" + text + "%"));
        TypedQuery<T> q = entityManager.createQuery(query);
        return q.getResultList();
    }

    @Override
    @Transactional
    public List<T> searchByField(String attr, Object value, String sortColumn, String sortOrder, int maxResults) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        query.distinct(true);
        Root<T> root = query.from(getDomainClass());

        if (StringUtils.isEmpty(sortColumn)) {
            sortColumn = attr;
        }

        if (StringUtils.isEmpty(sortOrder)) {
            sortOrder = "desc";
        }

        boolean isDesc = StringUtils.isEmpty(sortOrder) || "desc".equalsIgnoreCase(sortOrder);
        Order order = isDesc ? cb.desc(root.get(sortColumn)) : cb.asc(root.get(sortColumn));
        Predicate predicate = null;
        if (value instanceof String) {
            predicate = cb.like(root.<String>get(attr), "%" + value + "%");
        } else if (value instanceof Long || value instanceof Double || value instanceof Integer) {
            predicate = cb.equal(root.get(attr), value);
        }
        query.select(root).where(predicate).orderBy(order);
        TypedQuery<T> q = entityManager.createQuery(query);
        q.setMaxResults(maxResults);
        q.setFirstResult(0);
        return q.getResultList();
    }

//    public List<T> queryDistinct(String attr) {
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> query = builder.createQuery(getDomainClass());
//        Root<T> root = query.from(getDomainClass());
//
//        //query.select(root).distinct(true).where(builder.like(root.<String>get(attr), "%" + text + "%"));
//
//        TypedQuery<T> q = entityManager.createQuery(query);
//        return q.getResultList();
//    }

}
