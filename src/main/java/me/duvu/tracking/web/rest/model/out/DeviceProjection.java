package me.duvu.tracking.web.rest.model.out;

import me.duvu.tracking.domain.enumeration.DeviceStatus;
import me.duvu.tracking.web.rest.model.out.little.AccountLittle;
import me.duvu.tracking.web.rest.model.out.little.AlertProfileLitter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

/**
 * @author beou on 9/21/17 04:43
 * @version 1.0
 */
public interface DeviceProjection {
    Long getId();
    String getName();
    String getDescription();
    String getDeviceId();
    String getImei();

    Set<AccountLittle> getAccounts();

    Set<AlertProfileLitter> getAlertProfiles();

    @Value(value = "#{target.getVehicle() != null ? target.getVehicle.getId() : null }")
    Long getVehicleId();

    @Value(value = "#{target.getVehicle() != null ? target.getVehicle.getName() : null }")
    Long getVehicleName();

    String getIpAddress();

    Integer getPort();

    String getProtocol();

    @Value(value = "#{target.getTimeZoneStr() == null ? 'UTC' : target.getTimeZoneStr() }")
    String getTimeZone();

    Integer getMaxStoredDataTime();

    Date getExpiredOn();

    DeviceStatus getStatus();

    String getSerialNumber();

    String getModelName();

    String getManufacturerName();

    String getFirmwareVerison();

    String getOriginalCountry();

    @Value(value = "#{target.getLastEventTime() != null ? target.getLastEventTime().getTime() : 0 }")
    Long getLastEventTime();
    Double getLastSpeedKph();
    Double getLastLatitude();
    Double getLastLongitude();
    String getLastAddress();

    @Value(value = "#{target.getStayedTime() != null ? target.getStayedTime().getTime() : 0 }")
    Long getStayedTime();

    String getCreatedBy();
    String getUpdatedBy();
    Date getCreatedOn();
    Date getUpdatedOn();
}
