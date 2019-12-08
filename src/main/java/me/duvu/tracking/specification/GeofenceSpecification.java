package me.duvu.tracking.specification;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.Account_;
import me.duvu.tracking.domain.Geofence;
import me.duvu.tracking.domain.Geofence_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author beou on 2/20/18 02:07
 */

@Slf4j
@Component
public class GeofenceSpecification extends AbstractSpecification<Geofence> {

    @Override
    public Specification<Geofence> search(String search) {
        return (root, query, cb) -> {
            query.distinct(true);

            List<String> roles = ApplicationContext.getRoles();
            Predicate pAccount = cb.and();

            if (!roles.contains("SUPER")) {
                Join<Geofence, Account> geofenceAccountJoin = root.join(Geofence_.accounts);
                pAccount = cb.equal(geofenceAccountJoin.get(Account_.accountId), ApplicationContext.getCurrentUserName());
            }
            Predicate pSearch = cb.or(
                    cb.like(root.get(Geofence_.name), normalize(search)),
                    cb.like(root.get(Geofence_.geojson), normalize(search))
            );

            return cb.and(pAccount, pSearch);
        };
    }
}
