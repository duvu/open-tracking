package me.duvu.tracking.entities.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author beou on 7/13/18 16:06
 */

public enum AlertType {
    ALERT_CUSTOM        ("ALERT_CUSTOM"),            // no param
    ALERT_START         ("ALERT_START"),              // no param
    ALERT_STOP          ("ALERT_STOP"),               //no param
    ALERT_ENGINE_START  ("ALERT_ENGINE_START"),       //no param
    ALERT_ENGINE_STOP   ("ALERT_ENGINE_STOP"),        //no param
    ALERT_OVER_SPEED    ("ALERT_OVER_SPEED"),         //param - speedKph
    ALERT_GEOFENCE_IN   ("ALERT_GEOFENCE_IN"),        //param - geofenceId
    ALERT_GEOFENCE_OUT  ("ALERT_GEOFENCE_OUT"),       //param - geofenceId
    ALERT_IGNITION_ON   ("ALERT_IGNITION_ON"),        //no param
    ALERT_IGNITION_OFF  ("ALERT_IGNITION_OFF"),       //no param
    ALERT_FUEL_DROP     ("ALERT_FUEL_DROP"),          //no param
    ALERT_FUEL_FILL     ("ALERT_FUEL_FILL");          //no param

    private String type;

    AlertType(String type) {
        this.type = type;
    }

    AlertType get(String type) {
        if (!StringUtils.isEmpty(type)) {
            switch (type.toUpperCase()) {
                case "START":
                    return ALERT_START;
                case "STOP":
                    return ALERT_STOP;
                case "ENGINE_START":
                    return ALERT_ENGINE_START;
                case "ENGINE_STOP":
                    return ALERT_ENGINE_STOP;
                case "OVER_SPEED":
                    return ALERT_OVER_SPEED;
                case "GEOFENCE_IN":
                    return ALERT_GEOFENCE_IN;
                case "GEOFENCE_OUT":
                    return ALERT_GEOFENCE_OUT;
                case "IGNITION_ON":
                    return ALERT_IGNITION_ON;
                case "IGNITION_OFF":
                    return ALERT_IGNITION_OFF;
                case "FUEL_DROP":
                    return ALERT_FUEL_DROP;
                case "FUEL_FILL":
                    return ALERT_FUEL_FILL;
                default:
                    return ALERT_CUSTOM;
            }
        }else {
            return ALERT_CUSTOM;
        }
    }

    @Override
    public String toString() {
        return type;
    }
}
