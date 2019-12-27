package me.duvu.tracking.web.rest.model.response;

/**
 * @author beou on 10/2/18 20:31
 */
public interface LocalOnlyEventDataProjection {
    Long getId();
    double getLatitude();
    double getLongitude();
    double getSpeedKPH();
    double getBatteryLevel();
    double getFuelLevel();

    double getFuelLevel2();

    double getFuelRemain();
    int getStatus();
}
