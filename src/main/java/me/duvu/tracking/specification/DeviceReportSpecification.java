package me.duvu.tracking.specification;

import me.duvu.tracking.domain.EventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * @author beou on 5/1/18 01:23
 */
@Slf4j
@Component
public class DeviceReportSpecification extends AbstractSpecification<EventData> {

    @Override
    public Specification<EventData> search(String search) {
        return null;
    }

    @Override
    public Specification<EventData> findOne(Long id) {
        return null;
    }

}
