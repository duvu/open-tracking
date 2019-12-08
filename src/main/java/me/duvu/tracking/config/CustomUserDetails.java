package me.duvu.tracking.config;

import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.enumeration.AccountStatus;
import me.duvu.tracking.domain.enumeration.Roles;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author beou on 8/21/17 14:53
 */

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1015478378277111822L;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private Account account;
    private Long accountId;

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl) {
        this.firstPageUrl = firstPageUrl;
    }

    private String firstPageUrl;
    private Roles maxRole = Roles.ANONYMOUS;

    CustomUserDetails(Account account) {
        this.account = account;
        this.init();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Roles p = account.getPrivilege();
        return Collections.singletonList(new SimpleGrantedAuthority(p.text()));
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getAccountId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;

    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getStatus().equals(AccountStatus.ACTIVATED);
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        this.init();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Roles getMaxRole() {
        return maxRole;
    }

    public void setMaxRole(Roles maxRole) {
        this.maxRole = maxRole;
    }


    public String getTimezoneStr() {
        return account.getTimeZoneStr();
    }

    private void init() {
        if (account != null) {
            this.accountId = account.getId();
            setFirstPageUrl(account.getFirstPageUrl());
            maxRole = account.getPrivilege();
        } else {
            throw new AccessDeninedOrNotExisted("Account not existed");
        }
    }
}
