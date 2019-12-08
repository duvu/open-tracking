package me.duvu.tracking.repository;

import me.duvu.tracking.domain.UnknownDevice;
import org.springframework.stereotype.Repository;

/**
 * @author beou on 10/22/18 21:24
 */

@Repository
public interface UnknownDeviceRepository extends Vd5CommonRepository<UnknownDevice> {
    UnknownDevice getByUniqueId(String uniqueId);
    void deleteByUniqueId(String uniqueId);
}
