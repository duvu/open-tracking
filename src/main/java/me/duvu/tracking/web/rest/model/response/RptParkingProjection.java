package me.duvu.tracking.web.rest.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author beou on 5/16/18 23:32
 */
public interface RptParkingProjection {
    @Value(value = "#{target.getDevice() != null ? target.getDevice().getId() : null}")
    Long getDevId();

    @Value(value = "#{target.getDevice() != null ? target.getDevice().getDeviceId() : null}")
    String getDeviceId();

    @Value(value = "#{target.getDevice() != null ? target.getDevice().getName() : null}")
    String getDeviceName();

    String getStatus();

    double getLatitude();

    double getLongitude();

    String getAddress();

    Long getTimestamp();

    double getSpeedKPH();

    double getAltitude();

    double getHeading();

    double getOdometerKM();

    Long getStartParkingTime();
    Long getEndParkingTime();

    Long getStoppedTime();
}
