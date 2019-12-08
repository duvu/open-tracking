package me.duvu.tracking.web.rest.model.out;

import org.springframework.beans.factory.annotation.Value;

public interface TrackProjection {
    Long getId();


    @Value(value = "#{target.getDevice() != null ? target.getDevice().getId() : null}")
    Long getDevId();

    @Value(value = "#{target.getDevice() != null ? target.getDevice().getDeviceId() : null}")
    String getDeviceId();

    @Value(value = "#{target.getDevice() != null ? target.getDevice().getName() : null}")
    String getDeviceName();

    double getLatitude();

    double getLongitude();

    String getAddress();

    Long getTimestamp();

    double getOdometerKM();

    double getSpeedKPH();

    double getAltitude();

    double getHeading();

    int getSatelliteCount();

    double getBatteryLevel();

    double getSignalStrength();

    double getFuelLevel();

    double getFuelLevel2();

    double getFuelRemain();

    @Value(value = "#{@statusCode.statusCodeToString(target.getStatus())}")
    String getStatus();
}
