package me.duvu.tracking.repository;

import me.duvu.tracking.domain.EventData;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author beou on 10/27/17 13:47
 */
@Repository
public interface EventDataRepository extends Vd5CommonRepository<EventData>, CustomEventDataRepository {
    void deleteByCreatedOnBefore(Date expiredDate);
}
