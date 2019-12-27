package me.duvu.tracking.web.rest.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author beou on 4/30/18 17:09
 */

public interface RptSpeedProjection {
    @Value(value = "#{target.getDevice() != null ? target.getDevice().getId() : null}")
    Long getDevId();

    @Value(value = "#{target.getDevice() != null ? target.getDevice().getDeviceId() : null}")
    String getDeviceId();

    @Value(value = "#{target.getDevice() != null ? target.getDevice().getName() : null}")
    String getDeviceName();

    //int getStatus();
    @Value(value = "#{@statusCode.statusCodeToString(target.getStatus())}")
    String getStatus();

    double getLatitude();

    double getLongitude();

    String getAddress();

    Long getTimestamp();

    double getSpeedKPH();

    double getAltitude();

    double getHeading();

    double getOdometerKM();
}
