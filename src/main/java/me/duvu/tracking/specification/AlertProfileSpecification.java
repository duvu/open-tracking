package me.duvu.tracking.specification;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.entities.Account_;
import me.duvu.tracking.entities.AlertProfile;
import me.duvu.tracking.entities.AlertProfile_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;

/**
 * @author beou on 8/4/18 02:32
 */

@Slf4j
@Component
public class AlertProfileSpecification extends AbstractSpecification<AlertProfile> {
    @Override
    public Specification<AlertProfile> search(String search) {
        return (root, query, cb) -> {
            query.distinct(true);
            Predicate pAccount = cb.equal(root.get(AlertProfile_.account).get(Account_.accountId), ApplicationContext.getCurrentUserName());
            Predicate pSearch = cb.or(
                    cb.like(root.get(AlertProfile_.name), normalize(search)),
                    cb.like(root.get(AlertProfile_.description), normalize(search)));

            return cb.and(pAccount, pSearch);
        };
    }

    @Override
    public Specification<AlertProfile> findOne(Long id) {
        return (root, query, cb) -> {
            return cb.equal(root.get(AlertProfile_.id), id);
        };
    }
}
