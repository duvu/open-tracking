package me.duvu.tracking.web.rest.model.response;

import java.util.Date;

/**
 * @author beou on 10/22/18 23:03
 */
public interface UnknownDeviceProjection {
    Long getId();
    String getUniqueId();
    int getPort();
    String getRemoteIpAddress();
    Date getCreationOn();

}
