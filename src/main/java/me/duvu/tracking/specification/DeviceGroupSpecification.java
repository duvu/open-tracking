package me.duvu.tracking.specification;

import me.duvu.tracking.domain.DeviceGroup;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author beou on 12/9/18 22:08
 */
@Component
public class DeviceGroupSpecification extends AbstractSpecification<DeviceGroup> {
    @Override
    public Specification<DeviceGroup> search(String search) {

        return (Specification<DeviceGroup>) (root, criteriaQuery, criteriaBuilder) -> null;
    }
}
