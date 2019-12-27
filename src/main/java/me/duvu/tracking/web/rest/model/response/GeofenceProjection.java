package me.duvu.tracking.web.rest.model.response;

/**
 * @author beou on 2/19/18 17:21
 */
public interface GeofenceProjection {
    Long getId();
    String getName();
    String getAddress();
    String getColor();
    double getMaxSpeedKPH();
    boolean isReverseGeocode();
    boolean isPrivateArea();
    String getGeojson();
}
