package me.duvu.tracking.internal.rest;

import me.duvu.tracking.entities.enumeration.DeviceStatus;
import me.duvu.tracking.web.rest.model.response.little.AccountLittle;
import me.duvu.tracking.web.rest.model.response.little.AlertProfileLitter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

/**
 * @author beou on 9/21/17 04:43
 * @version 1.0
 */
public interface InternalDeviceProjection {
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
}
