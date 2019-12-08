package me.duvu.tracking.specification;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.Account_;
import me.duvu.tracking.domain.Device;
import me.duvu.tracking.domain.Device_;
import me.duvu.tracking.domain.enumeration.DeviceStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;

/**
 * @author beou on 10/23/17 16:12
 */

@Component
public class DeviceSpecification extends AbstractSpecification<Device> {
    @Override
    public Specification<Device> search(String search) {
        return (root, query, cb) -> {
            query.distinct(true);
            Predicate pEnabled = cb.notEqual(root.get(Device_.status), DeviceStatus.DISABLED);

            List<String> roles = ApplicationContext.getRoles();
            Predicate pAccount = cb.and();
            if (!roles.contains("SUPER")) {
                Join<Device, Account> deviceAccountJoin = root.join(Device_.accounts);
                pAccount = cb.equal(deviceAccountJoin.get(Account_.accountId), ApplicationContext.getCurrentUserName());
            }

            Predicate predicate = cb.or(
                    cb.like(root.get(Device_.deviceId), normalize(search)),
                    cb.like(root.get(Device_.name), normalize(search)));

            return cb.and(pAccount, pEnabled, predicate);
        };
    }

    @Override
    public Specification<Device> findOne(Long id) {
        return (root, query, cb) -> cb.equal(root.get(Device_.id), id);
    }

    public Specification<Device> queryByStatus(DeviceStatus status) {
        return (root, query, cb) -> cb.equal(root.get(Device_.status), status);
    }

    public Specification<Device> queryAllExpiredToday() {
        Date today = new Date();
        return (root, query, cb) -> cb.and(
                cb.lessThan(root.get(Device_.expiredOn), today),
                cb.notEqual(root.get(Device_.status), DeviceStatus.EXPIRED)
        );
    }

    public Specification queryByAccountId(Long accountId) {
        return (root, query, cb) -> cb.equal(root.join(Device_.accounts).get(Account_.id), accountId);
    }
}
