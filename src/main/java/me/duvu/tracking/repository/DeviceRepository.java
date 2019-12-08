package me.duvu.tracking.repository;


import me.duvu.tracking.domain.Device;
import org.springframework.stereotype.Repository;

/**
 * @author beou on 8/1/17 03:32
 * @version 1.0
 */
@Repository
public interface DeviceRepository extends Vd5CommonRepository<Device> {
    Device findByDeviceId(String deviceId);
}
