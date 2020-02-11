package me.duvu.tracking.entities.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * @author beou on 8/7/18 03:51
 */
public enum Roles {
    ANONYMOUS   ("ANONYMOUS"),
    NORMAL_USER ("NORMAL_USER"),
    MODERATOR   ("MODERATOR"),
    ADMIN       ("ADMIN"),
    SYSADMIN    ("SYSADMIN"),
    SUPER       ("SUPER");

    private String text;
    Roles(String text) {
        this.text = text;
    }


    public int value() {
        return ordinal();
    }
    public String text() {
        return this.text;
    }

    public static Roles get(int value) {
        if (value >= 5) {
            return SUPER;
        } else if (value >= 4) {
            return SYSADMIN;
        } else if (value >= 3) {
            return ADMIN;
        } else if (value >= 2) {
            return MODERATOR;
        } else if (value >= 1) {
            return NORMAL_USER;
        } else {
            return ANONYMOUS;
        }
    }

    public static Roles get(String text) {
        if (StringUtils.isEmpty(text)) {
            return ANONYMOUS;
        }
        switch (text.toUpperCase()) {
            case "SUPER":
                return SUPER;
            case "SYSADMIN":
                return SYSADMIN;
            case "ADMIN":
                return ADMIN;
            case "MODERATOR":
                return MODERATOR;
            case "NORMAL_USER":
                return NORMAL_USER;
            default:
                return ANONYMOUS;
        }
    }
}
