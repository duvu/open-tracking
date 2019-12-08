package me.duvu.tracking.domain.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author beou on 10/16/17 21:14
 */

public enum AccountStatus {
        UNKNOWN     ("UNKNOWN"),
        DELETED     ("DELETED"),
        PENDING     ("PENDING"),
        INACTIVATED ("INACTIVATED"),
        ACTIVATED   ("ACTIVATED");

    private final String status;

    AccountStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static AccountStatus get(String status) {
        if (StringUtils.isEmpty(status)) {
            return UNKNOWN;
        }

        switch (status.toUpperCase()) {
            case "ACTIVATED":
                return ACTIVATED;
            case "INACTIVATED":
                return INACTIVATED;
            case "PENDING":
                return PENDING;
            case "DELETED":
                return DELETED;
            default:
                return UNKNOWN;
        }
    }

    public static AccountStatus[] getAll() {
        return new AccountStatus[] {UNKNOWN, DELETED, PENDING, INACTIVATED, ACTIVATED};
    }
}
