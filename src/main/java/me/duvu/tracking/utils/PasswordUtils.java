package me.duvu.tracking.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {
    private static PasswordEncoder passwordEncoder;

    public static void init(PasswordEncoder encoder) {
        passwordEncoder = encoder;
    }

    public static String encode(String passwd) {
        return passwordEncoder.encode(passwd);
    }

    public static boolean matches(CharSequence charSequence, String encodedPasswd) {
        return passwordEncoder.matches(charSequence, encodedPasswd);
    }

    public static String getRandom(int length, boolean letter, boolean number) {
        return RandomStringUtils.random(length, letter, number);
    }

    public static String getRandom() {
        return getRandom(8, true, true);
    }
}
