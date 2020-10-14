package me.duvu.tracking.services.dto;

import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.EventData;
import lombok.Data;

import java.io.Serializable;

/**
 * @author beou on 5/17/18 00:06
 */

@Data
public class ParkingEventDTO implements Serializable {
    private static final long serialVersionUID = -9156408444978067054L;

    Long devId;
    String deviceId;
    String deviceName;
    String status;

    Device device;

    double latitude;
    double longitude;
    double altitude;
    double heading;

    String address;
    Long timestamp;
    double odometerKM;
    double speedKPH;

    Long startParkingTime;
    Long endParkingTime;

    Double startParkingOdometterKM;
    Double endParkingOdometterKM;

    Long stoppedTime;

    public static ParkingEventDTO from(EventData evdt) {
        ParkingEventDTO ped = new ParkingEventDTO();

        ped.setDevId(evdt.getDevice().getId());
        ped.setDeviceId(evdt.getDevice().getDeviceId());

        ped.setDevice(evdt.getDevice());

        ped.setLatitude(evdt.getLatitude());
        ped.setLongitude(evdt.getLongitude());
        ped.setAltitude(evdt.getAltitude());
        ped.setAddress(evdt.getAddress());
        ped.setTimestamp(evdt.getTimestamp());



        ped.setSpeedKPH(evdt.getSpeedKPH());
        ped.setHeading(evdt.getHeading());

        ped.setStatus(evdt.getStatusCode());
        ped.setOdometerKM(evdt.getOdometerKM());

        return ped;
    }
}
