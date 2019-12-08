package me.duvu.tracking.specification;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author beou on 10/22/18 21:26
 */
@Component
public class UnknownDeviceSpecification extends AbstractSpecification<UnknownDevice> {

    @Override
    public Specification<UnknownDevice> search(String search) {
        return (root, query, cb) -> {
            query.distinct(true);
            List<String> roles = ApplicationContext.getRoles();
            if (roles.contains("SUPER")) {
                return cb.like(root.get(UnknownDevice_.uniqueId), normalize(search));
            } else {
                return null;
            }
        };
    }
}
