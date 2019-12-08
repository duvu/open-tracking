package me.duvu.tracking.specification;

import me.duvu.tracking.domain.Driver;
import me.duvu.tracking.domain.Driver_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author beou on 9/20/18 11:54
 */
@Component
public class DriverSpecification extends AbstractSpecification<Driver> {
    @Override
    public Specification<Driver> search(String search) {
        return (root, query, cb) -> {
            query.distinct(true);

            return cb.or(
                    cb.like(root.get(Driver_.firstName), normalize(search)),
                    cb.like(root.get(Driver_.lastName), normalize(search)),
                    cb.like(root.get(Driver_.driverLicenseNumber), normalize(search)));
        };
    }
}
