package me.duvu.tracking.web.rest.model.out;

import java.util.Date;
import java.util.Set;

/**
 * @author beou on 12/4/18 08:12
 */
public interface DeviceGroupProjection extends AbstractProjection{
    Long getId();
    String getName();
    String getDescription();
    Set<DeviceProjection> getDevices();
    String getCreatedBy();
    String getUpdatedBy();
    Date getCreatedOn();
    Date getUpdatedOn();

    int getTotalDevices();
    int getLiveDevices();
}
