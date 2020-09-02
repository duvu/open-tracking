package me.duvu.tracking.aop.monitor;

import me.duvu.tracking.external.email.EmailService;
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
import org.springframework.util.Assert;

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
    private final EmailService emailService;

    public AlertMonitoringAspect(DeviceRepository deviceRepository, GeofenceRepository geofenceRepository,
                                 AlertEventLogRepository alertEventLogRepository, EmailService emailService) {
        this.deviceRepository = deviceRepository;
        this.geofenceRepository = geofenceRepository;
        this.alertEventLogRepository = alertEventLogRepository;
        this.emailService = emailService;
    }

    @Before(value = "execution(* me.duvu.tracking.internal.PositionService.add(..))")
    public void notifyAlerts(JoinPoint joinPoint) {
        Position position = (Position) joinPoint.getArgs()[0];
        String deviceId = position.getDeviceId();
        Device device = deviceRepository.findByDeviceId(deviceId);

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
        double speedKph = alert.getSpeedKph();
        double overSpeedKph = position.getSpeed();
        if ((speedKph > 0) && (overSpeedKph > speedKph)) {
            saveAlertLog(device, alert, position);
            sendAlertMessageToEmail(device, alert, position);
        }
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
        // long zoneId = alert.getZoneId();
        Geofence geofence = alert.getZone(); //geofenceRepository.findById(zoneId).orElse(null);
        Assert.notNull(geofence, "Not found zone in alert#" + alert.getName());
        log.info("[>_] Geofence {} found.", geofence.getName());

        Double prevLatitude = device.getLastLatitude();
        Double prevLongitude = device.getLastLongitude();
        double currLatitude = position.getLatitude();
        double currLongitude = position.getLongitude();

        Geometry geometry = geofence.getGeometry();
        Assert.notNull(geometry, "Not found valid geometry #" + geofence.getGeojson());
        // 1. check if current location is outside geofence.
        // 2. check if previous location is inside geofence
        log.info("Geometry: {}", geometry.toGeoJson());
        if (!geometry.containsPoint(currLatitude, currLongitude) && geometry.containsPoint(prevLatitude, prevLongitude)) {
            //notify an Alert out
            log.info("Your vehicle just go out of the geofence: #" + geofence.getName());
            saveAlertLog(device, alert, position);
            sendAlertMessageToEmail(account, device, alert, position);
        } else {
            log.info("alert "  + alert.getName() + " not fired");
        }
    }
    private void notifyGeozoneIn(Device device, AlertProfile alert, Position position) {
        Account account = alert.getAccount();
        Geofence geofence = alert.getZone();
        Assert.notNull(geofence, "Not found zone in alert#" + alert.getName());
        log.info("[>_] Geofence {} found.", geofence.getName());

        Double prevLatitude = device.getLastLatitude();
        Double prevLongitude = device.getLastLongitude();
        double currLatitude = position.getLatitude();
        double currLongitude = position.getLongitude();

        Geometry geometry = geofence.getGeometry();
        Assert.notNull(geometry, "Not found valid geometry #" + geofence.getGeojson());
        // 1. check if current location is inside geofence
        // 2. check if previous location is outside geofence
        log.info("Geometry: {}", geometry.toGeoJson());
        if (geometry.containsPoint(currLatitude, currLongitude) && !geometry.containsPoint(prevLatitude, prevLongitude)) {
            log.info("Your vehicle just come into geofence: #" + geofence.getName());

            //1. store a log
            saveAlertLog(device, alert, position);
            sendAlertMessageToEmail(account, device, alert, position);
        } else {
            log.info("[>_] Alert "  + alert.getName() + " not fired");
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

    private void sendAlertMessageToEmail(Device device, AlertProfile alert, Position position) {
        Account account = alert.getAccount();
        sendAlertMessageToEmail(account, device, alert, position);
    }
    private void sendAlertMessageToEmail(Account account, Device device, AlertProfile alert, Position position) {
        String to = account.getEmailAddress();
        String subject = getSubject(alert, position);
        String body = getBody(alert);
        emailService.send(to, subject, body);
    }

    private String getSubject(AlertProfile alertProfile, Position position) {
        AlertType type = alertProfile.getType();
        String subject = "[Alert] ";
        Geofence zone = alertProfile.getZone();
        switch (type) {
            case ALERT_GEOFENCE_IN:
                subject += ("[Alert] Come In Geozone #" + (zone != null ? zone.getName() : "Not defined"));
                break;
            case ALERT_GEOFENCE_OUT:
                subject += ("[Alert] Come Out Geozone #" + (zone != null ? zone.getName() : "Not defined"));
                break;
            case ALERT_START:
                break;
            case ALERT_STOP:
                break;
            case ALERT_CUSTOM:
                break;
            case ALERT_FUEL_DROP:
                break;
            case ALERT_FUEL_FILL:
                break;
            case ALERT_OVER_SPEED:
                double speedKph = alertProfile.getSpeedKph();
                double overSpeedKph = position.getSpeed();
                subject += "[Alert] OverSpeed #" + overSpeedKph + "/" + speedKph + "(km/h)";
                break;
            case ALERT_ENGINE_START:
                break;
            case ALERT_ENGINE_STOP:
                break;
            case ALERT_IGNITION_ON:
                break;
            case ALERT_IGNITION_OFF:
                break;
        }
        return subject;
    }

    String getBody(AlertProfile alertProfile) {
        return "Body!";
    }
}
