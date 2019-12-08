package me.duvu.tracking.config;

import me.duvu.tracking.domain.Account;
import me.duvu.tracking.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author beou on 8/21/17 14:44
 * */

@Slf4j
@Component
@Qualifier("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Assert.notNull(userName, "Not a valid userName.");

        Account account = this.accountRepository.findAccountByAccountId(userName);
        if (account == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        CustomUserDetails principal = new CustomUserDetails(account);
        return principal;
    }
}
