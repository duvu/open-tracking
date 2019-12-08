package me.duvu.tracking.repository;

import me.duvu.tracking.domain.Geofence;
import org.springframework.stereotype.Repository;

/**
 * @author beou on 2/20/18 02:04
 */
@Repository
public interface GeofenceRepository extends Vd5CommonRepository<Geofence> {
}
