package me.duvu.tracking;

import me.duvu.tracking.config.CustomUserDetails;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.SmtpProperties;
import me.duvu.tracking.domain.enumeration.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author beou on 10/17/17 22:21
 */

public class ApplicationContext {
    private static CustomUserDetails getCustomUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getCurrentUserName() {
        return getCustomUserDetails().getUsername();
    }

    public static Long getAccountId() {
        return getCustomUserDetails().getAccountId();
    }

    public static Account getAccount() {
        return getCurrentAccount();
    }

    public static Account getCurrentAccount() {
        return getCustomUserDetails().getAccount();
    }

    public static Set<SmtpProperties> getSmtpProperties() {
        return getCurrentAccount().getSmtpProperties();
    }

    public static SmtpProperties getSmtpProperties(Long smtpId) {
        return getSmtpProperties().stream().filter(x -> x.getId().equals(smtpId)).findFirst().orElse(null);
    }

    public static String getTimezoneStr() {
        return getCustomUserDetails().getTimezoneStr();
    }


    public static TimeZone getTimezone() {
        return TimeZone.getTimeZone(getTimezoneStr());
    }

    public static List<String> getRoles() {
        return getCustomUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public static List<Roles> getRolesInEnum() {
        return getCustomUserDetails().getAuthorities().stream().map(GrantedAuthority::getAuthority).map(Roles::get).collect(Collectors.toList());
    }


    public static boolean checkManager(Account account) {
        if (account != null) {
            return account.getManager().getId().equals(getAccountId());
        }
        return false;
    }
}
