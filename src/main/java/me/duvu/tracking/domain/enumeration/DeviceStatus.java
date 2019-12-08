package me.duvu.tracking.domain.enumeration;

/**
 * @author beou on 1/17/18 12:51
 */
public enum  DeviceStatus {
    UNKNOWN("unknown"),
    DISABLED("disabled"),
    ENABLED("enabled"),
    EXPIRED("expired"),
    INACTIVE("inactive"),
    ACTIVE("active");

    private final String status;

    DeviceStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static DeviceStatus get(String status) {
        if ("enabled".equalsIgnoreCase(status)) {
            return ENABLED;
        } else if ("expired".equalsIgnoreCase(status)) {
            return EXPIRED;
        } else if ("disabled".equalsIgnoreCase(status)) {
            return DISABLED;
        } else if ("active".equalsIgnoreCase(status)) {
            return ACTIVE;
        } else if ("inactive".equalsIgnoreCase(status)) {
            return INACTIVE;
        } else {
            return UNKNOWN;
        }
    }
    public static DeviceStatus[] getAll() {
        return new DeviceStatus[]{UNKNOWN, DISABLED, EXPIRED, ENABLED};
    }
}
