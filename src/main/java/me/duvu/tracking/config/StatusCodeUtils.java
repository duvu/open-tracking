package me.duvu.tracking.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author beou on 9/2/18 23:38
 */

public class StatusCodeUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static StatusCodeUtils statusCodeUtilsInstance;

    private StatusCodeUtils() {}

    public static StatusCodeUtils getInstance() {
        if (statusCodeUtilsInstance == null) {
            statusCodeUtilsInstance = new StatusCodeUtils();
        }
        return statusCodeUtilsInstance;
    }

    public String statusCodeToString(int statusCode) {
        switch (statusCode) {
            case StatusCodes.STATUS_IGNORE:
                return "IGNORE";
            case StatusCodes.STATUS_NONE:
                return "NONE";
            case StatusCodes.STATUS_LOCATION:
                return "LOCATION";
            case StatusCodes.STATUS_LAST_LOCATION:
                return "LAST LOCATION";
            case StatusCodes.STATUS_MOTION_START:
                return "MOTION START";
            case StatusCodes.STATUS_MOTION_EN_ROUTE:
                return "IN MOTION/EN ROUTE";
            case StatusCodes.STATUS_MOTION_STOP:
            case StatusCodes.STATUS_MOTION_DORMANT:
            case StatusCodes.STATUS_MOTION_STOPPED:
                return "STOPPED";
            case StatusCodes.STATUS_MOTION_IDLE:
                return "IDLE";
            case StatusCodes.STATUS_MOTION_IDLE_END:
                return "IDLE END";
            case StatusCodes.STATUS_MOTION_EXCESS_IDLE:
                return "EXESS IDLE";
            case StatusCodes.STATUS_LOW_SPEED:
                return "LOW SPEED";
            case StatusCodes.STATUS_MOTION_EXCESS_SPEED:
                return "EXCESS SPEED/OVER SPEED";
            case StatusCodes.STATUS_MOTION_MOVING:
                return "MOVING";
            case StatusCodes.STATUS_MOTION_STOP_PENDING:
                return "STOP PENDING";
            case StatusCodes.STATUS_MOTION_CHANGE:
                return "MOTION CHANGE";
            case StatusCodes.STATUS_MOTION_HEADING:
                return "CHANGE DIRECTION";
            case StatusCodes.STATUS_MOTION_ACCELEROMETER:
                return "MOTION ACCELEROMETER";
            case StatusCodes.STATUS_MOTION_ACCELERATION:
                return "MOTION ACCELERATION";
            case StatusCodes.STATUS_MOTION_DECELERATION:
                return "MOTION DECELERATION";
            case StatusCodes.STATUS_TRACK_START:
                return "TRACK START";
            case StatusCodes.STATUS_TRACK_LOCATION:
                return "TRACK LOCATION";
            case StatusCodes.STATUS_TRACK_STOP:
                return "TRACK STOP";
            case StatusCodes.STATUS_GEOFENCE_ARRIVE:
                return "GEOFENCE ARRIVE";
            case StatusCodes.STATUS_CORRIDOR_ARRIVE:
                return "CORRIDOR ARRIVE";
            case StatusCodes.STATUS_JOB_ARRIVE:
                return "JOB ARRIVE";
            case StatusCodes.STATUS_GEOFENCE_DEPART:
                return "GEOFENCE DEPART";
            case StatusCodes.STATUS_CORRIDOR_DEPART:
                return "CORRIDOR DEPART";
            case StatusCodes.STATUS_JOB_DEPART:
                return "JOB DEPART";
            case StatusCodes.STATUS_GEOFENCE_VIOLATION:
                return "GEOFENCE VIOLATION";
            case StatusCodes.STATUS_CORRIDOR_VIOLATION:
                return "CORRIDOR VIOLATION";
            case StatusCodes.STATUS_GEOFENCE_ACTIVE:
                return "GEOFENCE ACTIVE";
            case StatusCodes.STATUS_CORRIDOR_ACTIVE:
                return "CORRIDOR ACTIVE";
            case StatusCodes.STATUS_GEOFENCE_INACTIVE:
                return "GEOFENCE INACTIVE";
            case StatusCodes.STATUS_CORRIDOR_INACTIVE:
                return "CORRIDOR INACTIVE";
            case StatusCodes.STATUS_GEOBOUNDS_ENTER:
                return "GEOBOUNDS ENTER";
            case StatusCodes.STATUS_GEOBOUNDS_EXIT:
                return "GEOBOUNDS EXIT";
            case StatusCodes.STATUS_PARKED:
                return "PARKED";
            case StatusCodes.STATUS_EXCESS_PARK:
                return "EXCESS PARK";
            case StatusCodes.STATUS_UNPARKED:
                return "UNPARKED";
            case StatusCodes.STATUS_INPUT_STATE:
                return "INPUT STATE CHANGE";
            case StatusCodes.STATUS_IGNITION_ON:
                return "IGNITION ON";
            case StatusCodes.STATUS_INPUT_ON:
                return "INPUT ON";
            case StatusCodes.STATUS_INPUT_OFF:
                return "INPUT OFF";
            case StatusCodes.STATUS_IGNITION_OFF:
                return "IGNITON OFF";
            case StatusCodes.STATUS_ENGINE_START:
                return "ENGINE START";
            case StatusCodes.STATUS_ENGINE_STOP:
                return "ENGINE STOP";
            case StatusCodes.STATUS_DOOR_OPEN_0:
                return "DOOR OPEN 0";
            case StatusCodes.STATUS_DOOR_OPEN_1:
                return "DOOR OPEN 1";
            case StatusCodes.STATUS_DOOR_OPEN_2:
                return "DOOR OPEN 2";

            case StatusCodes.STATUS_SEATBELT_ON_0:
                return "SEATBELT ON 0";
            case StatusCodes.STATUS_SEATBELT_ON_1:
                return "SEATBELT ON 1";

            case StatusCodes.STATUS_DOOR_CLOSE_0:
                return "DOOR CLOSE 0";
            case StatusCodes.STATUS_DOOR_CLOSE_1:
                return "DOOR CLOSE 1";
            case StatusCodes.STATUS_DOOR_CLOSE_2:
                return "DOOR CLOSE 2";
            case StatusCodes.STATUS_SEATBELT_OFF_0:
                return "SEATBELT OFF 0";
            case StatusCodes.STATUS_SEATBELT_OFF_1:
                return "SEATBELT OFF 1";
            case StatusCodes.STATUS_ELAPSED_00:
            case StatusCodes.STATUS_ELAPSED_01:
            case StatusCodes.STATUS_ELAPSED_02:
            case StatusCodes.STATUS_ELAPSED_03:
            case StatusCodes.STATUS_ELAPSED_04:
            case StatusCodes.STATUS_ELAPSED_05:
            case StatusCodes.STATUS_ELAPSED_06:
            case StatusCodes.STATUS_ELAPSED_07:
                return "ELAPSED";

            case StatusCodes.STATUS_PANIC_ON:
                return "PANIC ON";
            case StatusCodes.STATUS_PANIC_OFF:
                return "PANIC OFF";
            case StatusCodes.STATUS_ALARM_ON:
                return "ALARM ON";
            case StatusCodes.STATUS_ALARM_OFF:
                return "ALARM OFF";
            case StatusCodes.STATUS_ASSIST_ON:
                return "ASSIST ON";
            case StatusCodes.STATUS_ASSIST_OFF:
                return "ASSIST OFF";
            case StatusCodes.STATUS_MANDOWN_ON:
                return "MANDOWN ON";
            case StatusCodes.STATUS_MANDOWN_OFF:
                return "MANDOWN OFF";
            case StatusCodes.STATUS_MEDICAL_ON:
                return "MEDICAL ON";
            case StatusCodes.STATUS_MEDICAL_OFF:
                return "MEDICAL OFF";
            case StatusCodes.STATUS_DRIVER_FATIGUE:
                return "DRIVER FATIGUE";
            case StatusCodes.STATUS_DRIVER_SLEEP:
                return "DRIVER SLEEP";
            case StatusCodes.STATUS_DRIVER_WAKE:
                return "DRIVER WAKE";

            case StatusCodes.STATUS_TOWING_START:
                return "TOWING START";
            case StatusCodes.STATUS_TOWING_STOP:
                return "TOWING STOP";
            case StatusCodes.STATUS_INTRUSION_ON:
                return "INTRUSION ON";
            case StatusCodes.STATUS_INTRUSION_OFF:
                return "INTRUSION OFF";

            case StatusCodes.STATUS_TAMPER_ON:
                return "TAMPER ON";
            case StatusCodes.STATUS_TAMPER_OFF:
                return "TAMPER OFF";
            case StatusCodes.STATUS_VIBRATION_ON:
                return "VIBRATION ON";
            case StatusCodes.STATUS_VIBRATION_OFF:
                return "VIBRATION OFF";
            case StatusCodes.STATUS_DOOR_LOCK:
                return "DOOR LOCK";
            case StatusCodes.STATUS_DOOR_UNLOCK:
                return "DOOR UNLOCK";
            case StatusCodes.STATUS_PTO_ON:
                return "PTO ON";
            case StatusCodes.STATUS_PTO_OFF:
                return "PTO OFF";

            //OBD STATUS
            case StatusCodes.STATUS_ONLINE:
                return "ONLINE";
            case StatusCodes.STATUS_OFFLINE:
                return "OFFLINE";
            case StatusCodes.STATUS_OBD_INFO_0:
                return "OBD INFO 0";
            case StatusCodes.STATUS_OBD_INFO_1:
            case StatusCodes.STATUS_OBD_INFO_2:
            case StatusCodes.STATUS_OBD_INFO_3:
            case StatusCodes.STATUS_OBD_INFO_4:
            case StatusCodes.STATUS_OBD_INFO_5:
            case StatusCodes.STATUS_OBD_INFO_6:
            case StatusCodes.STATUS_OBD_INFO_7:
                return "OBD INFO";
            case StatusCodes.STATUS_OBD_CONNECT:
                return "OBD CONNECT";
            case StatusCodes.STATUS_OBD_DISCONNECT:
                return "OBD DISCONNECT";
            case StatusCodes.STATUS_OBD_FAULT:
                return "OBD FAULT";
            case StatusCodes.STATUS_CHECK_ENGINE:
                return "CHECK ENGINE";
            case StatusCodes.STATUS_OBD_RANGE:
                return "OBD OUT OF RANGE";
            case StatusCodes.STATUS_OBD_RPM_RANGE:
                return "OBD RPM OUT OF RANGE";
            case StatusCodes.STATUS_OBD_FUEL_RANGE:
                return "J1708 FUEL LEVEL OUT OF RANGE";
            case StatusCodes.STATUS_OBD_OIL_RANGE:
                return "J1708 OIL OUT OF RANGE";
            case StatusCodes.STATUS_OBD_TEMP_RANGE:
                return "J1708 TEMPERATURE OUT OF RANGE";
            case StatusCodes.STATUS_OBD_LOAD_RANGE:
                return "J1708 ENGINE LOAD OUT-OF-RANGE";
            case StatusCodes.STATUS_OBD_COOLANT_RANGE:
                return "J1708 COOLANT OUT-OF-RANGE";
            case StatusCodes.STATUS_EXCESS_BRAKING:
            case StatusCodes.STATUS_EXCESS_BRAKING_2:
            case StatusCodes.STATUS_EXCESS_BRAKING_3:
                return "EXCESSIVE/HARSH DECELERATION DETECTED";
            case StatusCodes.STATUS_EXCESS_CORNERING:
            case StatusCodes.STATUS_EXCESS_CORNERING_2:
            case StatusCodes.STATUS_EXCESS_CORNERING_3:
                return "EXCESS CORNERING";
            case StatusCodes.STATUS_IMPACT:
            case StatusCodes.STATUS_IMPACT_DATA_1:
            case StatusCodes.STATUS_IMPACT_DATA_2:
            case StatusCodes.STATUS_IMPACT_DATA_3:
                return "IMPACT DATA";
            case StatusCodes.STATUS_FREEFALL:
                return "FREEALL";
            case StatusCodes.STATUS_FUEL_REFILL:
                return "FUEL REFILL";
            case StatusCodes.STATUS_FUEL_THEFT:
                return "FUEL THEFT";
            case StatusCodes.STATUS_LOW_FUEL:
                return "LOW FUEL";
            case StatusCodes.STATUS_FUEL_DIRTY:
                return "FUEL DIRTY";
            case StatusCodes.STATUS_FUEL_SENSOR:
                return "FUEL SENSOR FAILED";
            case StatusCodes.STATUS_EXCESS_ACCEL:
            case StatusCodes.STATUS_EXCESS_ACCEL_2:
            case StatusCodes.STATUS_EXCESS_ACCEL_3:
                return "EXCESSIVE ACCELERATION DETECTED";
            case StatusCodes.STATUS_DAY_SUMMARY:
                return "END OF DAY";
            case StatusCodes.STATUS_TRIP_SUMMARY:
                return "END OF TRIP";

            case StatusCodes.STATUS_TIRE_TEMP_RANGE:
                return "TIRE TEMPERATURE OUT-OF-RANGE";
            case StatusCodes.STATUS_TIRE_PRESSURE_RANGE:
                return "TIRE PRESSURE OUT-OF-RANGE";
            case StatusCodes.STATUS_TIRE_PRESSURE_LOW:
                return "TIRE PRESSURE LOW";
            case StatusCodes.STATUS_TIRE_BATTERY_LOW:
                return "TIRE SENSOR BATTERY LOW";
            case StatusCodes.STATUS_IP_ADDRESS:
                return "IP CHANGED";
            case StatusCodes.STATUS_SIM_CARD:
                return "SIM CHANGED";
            case StatusCodes.STATUS_BATTERY_VOLTS:
                return "BATTERY VOLT";
            case StatusCodes.STATUS_BACKUP_VOLTS:
                return "BACKUP VOLT";
            case StatusCodes.STATUS_BATT_CHARGE_ON:
                return "BATTERY CHARGING";
            case StatusCodes.STATUS_BATT_CHARGE_OFF:
                return "BATTERY CHARGE OFF";
            case StatusCodes.STATUS_BATTERY_LEVEL:
                return "BATTERY LEVEL";
            case StatusCodes.STATUS_POWER_FAILURE:
                return "POWER FAILURE";
            case StatusCodes.STATUS_POWER_ALARM:
                return "POWER ALARM";
            case StatusCodes.STATUS_POWER_RESTORED:
                return "POWER RESTORED";
            case StatusCodes.STATUS_POWER_OFF:
                return "POWER OFF";
            case StatusCodes.STATUS_POWER_ON:
                return "POWER ON";
            case StatusCodes.STATUS_GPS_EXPIRED:
                return "GPS EXPIRED";
            case StatusCodes.STATUS_GPS_FAILURE:
                return "GPS FAILURE";
            case StatusCodes.STATUS_GPS_ANTENNA_OPEN:
                return "GPS ANTENNA OPEN";
            case StatusCodes.STATUS_GPS_ANTENNA_SHORT:
                return "GPS ANTENNA SHORT";
            case StatusCodes.STATUS_GPS_JAMMING:
                return "GPS JAMMING";
            case StatusCodes.STATUS_GPS_RESTORED:
                return "GPS RESTORED";
            case StatusCodes.STATUS_GPS_LOST:
                return "GPS LOST";
            case StatusCodes.STATUS_DIAGNOSTIC:
                return "DIAGNOSTIC";
            case StatusCodes.STATUS_CONNECTION_FAILURE:
                return "MODEM/GPRS/CDMA FAILURE";
            case StatusCodes.STATUS_CONNECTION_RESTORED:
                return "MODEM/GPRS/CDMA RESTORED";
            case StatusCodes.STATUS_MODEM_FAILURE:
                return "MODEM FAILURE DETECTED";
            case StatusCodes.STATUS_INTERNAL_FAILURE:
                return "INTERNAL FAILURE";
            case StatusCodes.STATUS_MODEM_JAMMING:
                return "MODEM JAMMING";
            case StatusCodes.STATUS_CONFIG_RESET:
                return "CONFIGURATION RESET";
            case StatusCodes.STATUS_CONFIG_START:
                return "CONFIGURATION START";
            case StatusCodes.STATUS_CONFIG_COMPLETE:
                return "CONFIGURATION COMPLETE";
            case StatusCodes.STATUS_CONFIG_FAILED:
                return "CONFIGURATION FAILED";
            case StatusCodes.STATUS_SHUTDOWN:
                return "DEVICE SHUTDOWN";
            case StatusCodes.STATUS_SHUTDOWN_CANCELLED:
                return "DEVICE SHUTDOWN CANCELLED";
            case StatusCodes.STATUS_SUSPEND:
                return "DEVICE SLEEP/SUSPEND";
            case StatusCodes.STATUS_RESUME:
                return "DEVICE RESUME";
            case StatusCodes.STATUS_ROAMING_ON:
                return "MODEM ROAMING ON";
            case StatusCodes.STATUS_ROAMING_OFF:
                return "MODEM ROAMING OFF";
            case StatusCodes.STATUS_ROAMING_UNKNOWN:
                return "MODEM ROAMING UNKNOWN";
            default:
                return "LOCATION";
        }
    }
}
