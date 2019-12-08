package me.duvu.tracking.services.models;

import me.duvu.tracking.domain.Device;
import me.duvu.tracking.domain.EventData;
import lombok.Data;

/**
 * @author beou on 5/17/18 00:06
 */

@Data
public class ParkingEvent {
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

    public static ParkingEvent from(EventData evdt) {
        ParkingEvent ped = new ParkingEvent();

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
