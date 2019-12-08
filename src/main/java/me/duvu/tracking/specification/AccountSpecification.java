package me.duvu.tracking.specification;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.Account_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author beou on 10/7/17 01:02
 * @version 1.0
 */
@Component
@Slf4j
public class AccountSpecification extends AbstractSpecification<Account> {

    @Override
    public Specification<Account> search(String search) {
        return (root, query, cb) -> {
            query.distinct(true);

            Predicate seachPredicate = cb.or(
                    cb.like(root.get(Account_.accountId), normalize(search)),
                    cb.like(root.get(Account_.firstName), normalize(search)),
                    cb.like(root.get(Account_.lastName),  normalize(search))
            );

            List<String> roles = ApplicationContext.getRoles();
            Predicate manageByPred = cb.and();
            if (!roles.contains("SUPER")) {
                Join<Account, Account> selfJoin = root.join(Account_.manager, JoinType.LEFT);
                manageByPred = cb.or(
                        cb.equal(selfJoin.get(Account_.accountId), ApplicationContext.getCurrentUserName()),
                        cb.equal(root.get(Account_.id), ApplicationContext.getAccountId())
                        );
            }
            return cb.and(manageByPred, seachPredicate);
        };
    }

    @Override
    public Specification<Account> findOne(Long id) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.get(Account_.id), id);
        };
    }
}
