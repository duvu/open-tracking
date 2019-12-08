package me.duvu.tracking.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author beou on 10/6/17 16:01
 * @version 1.0
 */

public abstract class AbstractSpecification<T> {
    public Specification<T> queryAnd(Map<String, String> params) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = params.entrySet().stream().map(x -> cb.like(root.get(x.getKey()), x.getValue())).collect(Collectors.toList());
            if (predicateList != null && predicateList.size() > 0) {
                Predicate p0 = predicateList.get(0);
                for (Predicate aPredicateList : predicateList) {
                    p0 = cb.and(p0, aPredicateList);
                }
                return p0;
            }
            return null;
        };
    }

    //- todo: create sort
    public Specification<T> queryOr(Map<String, String> params) {
        return (root, query, cb) -> {
            List<Predicate> predicateList = params.entrySet().stream().map(x -> cb.like(root.get(x.getKey()), x.getValue())).collect(Collectors.toList());
            if (predicateList != null && predicateList.size() > 0) {
                Predicate p0 = predicateList.get(0);
                for (Predicate aL : predicateList) {
                    p0 = cb.or(p0, aL);
                }
                return p0;
            }
            return null;
        };
    }

    String normalize(String pattern) {
        if (StringUtils.isEmpty(pattern)) return "%";
        return "%" + pattern + "%";
    }

    public abstract Specification<T> search(String search);

    public Specification<T> findOne(Long id) {
        return (root, query, cb) -> {
            return cb.equal(root.get("id"), id);
        };
    }
}
