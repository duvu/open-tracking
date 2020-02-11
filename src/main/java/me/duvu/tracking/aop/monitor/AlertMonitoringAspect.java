package me.duvu.tracking.aop.monitor;

import me.duvu.tracking.entities.*;
import me.duvu.tracking.entities.enumeration.AlertType;
import me.duvu.tracking.geo.Geometry;
import me.duvu.tracking.internal.model.Position;
import me.duvu.tracking.repository.AlertEventLogRepository;
import me.duvu.tracking.repository.DeviceRepository;
import me.duvu.tracking.repository.GeofenceRepository;
import me.duvu.tracking.utils.AlertEventLogConverter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author beou on 8/16/19 16:28
 */
@Slf4j
@Aspect
@Component
public class AlertMonitoringAspect {
    private final DeviceRepository deviceRepository;
    private final GeofenceRepository geofenceRepository;
    private final AlertEventLogRepository alertEventLogRepository;

    public AlertMonitoringAspect(DeviceRepository deviceRepository, GeofenceRepository geofenceRepository,
                                 AlertEventLogRepository alertEventLogRepository) {
        this.deviceRepository = deviceRepository;
        this.geofenceRepository = geofenceRepository;
        this.alertEventLogRepository = alertEventLogRepository;
    }

    @Before(value = "execution(* me.duvu.tracking.internal.PositionService.add(..))")
    public void notifyAlerts(JoinPoint joinPoint) {
        Position position = (Position) joinPoint.getArgs()[0];
        long deviceId = position.getDeviceId();
        Device device = deviceRepository.findById(deviceId).orElse(null);

        if (device != null) {
            Set<AlertProfile> alertProfiles = device.getAlertProfiles();
            for (AlertProfile alert : alertProfiles) {
                notifyAlert(device, alert, position);
            }
        }

    }

    private void notifyAlert(Device device, AlertProfile alert, Position position) {
        AlertType alertType = alert.getType();
        if (alertType == AlertType.ALERT_GEOFENCE_IN) {
            notifyGeozoneIn(device, alert, position);

        } else if (alertType == AlertType.ALERT_GEOFENCE_OUT) {
            notifyGeozoneOut(device, alert, position);

        } else if (alertType == AlertType.ALERT_ENGINE_START) {
            notifyEngineStart(device, alert, position);

        } else if (alertType == AlertType.ALERT_ENGINE_STOP) {
            notifyEngineStop(device, alert, position);

        } else if (alertType == AlertType.ALERT_START) {
            notifyStart(device, alert, position);

        } else if (alertType == AlertType.ALERT_STOP) {
            notifyStop(device, alert, position);

        } else if (alertType == AlertType.ALERT_FUEL_DROP) {
            notifyFuelDrop(device, alert, position);

        } else if (alertType == AlertType.ALERT_FUEL_FILL) {
            notifyFuelFill(device, alert, position);

        } else if (alertType == AlertType.ALERT_IGNITION_OFF) {
            notifyIgnitionOff(device, alert, position);

        } else if (alertType == AlertType.ALERT_IGNITION_ON) {
            notifyIgnitionOn(device, alert, position);

        } else if (alertType == AlertType.ALERT_OVER_SPEED) {
            notifyOverSpeed(device, alert, position);

        } else if (alertType == AlertType.ALERT_CUSTOM) {
            notifyCustom(device, alert, position);

        }
    }

    private void notifyCustom(Device device, AlertProfile alert, Position position) {
        // add alert-event-log
    }

    private void notifyOverSpeed(Device device, AlertProfile alert, Position position) {
    }

    private void notifyIgnitionOn(Device device, AlertProfile alert, Position position) {
    }

    private void notifyIgnitionOff(Device device, AlertProfile alert, Position position) {
    }

    private void notifyFuelFill(Device device, AlertProfile alert, Position position) {
    }

    private void notifyFuelDrop(Device device, AlertProfile alert, Position position) {
    }

    private void notifyStop(Device device, AlertProfile alert, Position position) {
    }

    private void notifyStart(Device device, AlertProfile alert, Position position) {
    }

    private void notifyEngineStop(Device device, AlertProfile alert, Position position) {
    }

    private void notifyEngineStart(Device device, AlertProfile alert, Position position) {

    }

    private void notifyGeozoneOut( Device device, AlertProfile alert, Position position) {
        Account account = alert.getAccount();
        long zoneId = alert.getZoneId();
        Geofence geofence = geofenceRepository.findById(zoneId).orElse(null);

        Double prevLatitude = device.getLastLatitude();
        Double prevLongitude = device.getLastLongitude();
        Double currLatitude = position.getLatitude();
        Double currLongitude = position.getLongitude();

        Geometry geometry = geofence.getGeometry();
        if (geometry != null) {
            // 1. check if current location is outside geofence.
            // 2. check if previous location is inside geofence
            if (!geometry.containsPoint(currLatitude, currLongitude) && geometry.containsPoint(prevLatitude, prevLongitude)) {
                //notify an Alert out
                log.info("Your vehicle just go out of the geofence: #" + geofence.getName());

                //1. store a log
                //1. store a log
                saveAlertLog(device, alert, position);

            } else {
                log.info("alert "  + alert.getName() + "not fired");
            }
        }
    }
    private void notifyGeozoneIn(Device device, AlertProfile alert, Position position) {
        Account account = alert.getAccount();
        long zoneId = alert.getZoneId();
        Geofence geofence = geofenceRepository.findById(zoneId).orElse(null);

        Double prevLatitude = device.getLastLatitude();
        Double prevLongitude = device.getLastLongitude();
        Double currLatitude = position.getLatitude();
        Double currLongitude = position.getLongitude();
        Geometry geometry = geofence.getGeometry();
        // 1. check if current location is inside geofence
        // 2. check if previous location is outside geofence
        if (geometry != null) {
            if (geometry.containsPoint(currLatitude, currLongitude) && !geometry.containsPoint(prevLatitude, prevLongitude)) {
                log.info("Your vehicle just come into geofence: #" + geofence.getName());

                //1. store a log
                saveAlertLog(device, alert, position);
            } else {
                log.info("alert "  + alert.getName() + "not fired");
            }
        }
    }

    private void saveAlertLog(Device device, AlertProfile alert, Position position) {
        //1. store a log
        AlertEventLog eventLog = AlertEventLogConverter.from(alert);
        eventLog.setDeviceId(device.getId());
        eventLog.setUniqueId(device.getDeviceId());
        eventLog.setLatitude(position.getLatitude());
        eventLog.setLongitude(position.getLongitude());
        eventLog.setAddress(position.getAddress());
        eventLog.setTimestamp(position.getFixTime().getTime());

        alertEventLogRepository.save(eventLog);
    }
}
