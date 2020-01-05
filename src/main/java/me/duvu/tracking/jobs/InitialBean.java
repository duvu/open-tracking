package me.duvu.tracking.jobs;

import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.enumeration.AccountStatus;
import me.duvu.tracking.domain.enumeration.Roles;
import me.duvu.tracking.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.utils.PasswordUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author beou on 10/16/17 19:30
 */
@Slf4j
@Component
public class InitialBean {
    //to setup init data
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public InitialBean(AccountRepository accountRepository,
                       PasswordEncoder passwordEncoder) {

        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        log.info("[>_] Starting set-up system ...");
        initAccounts();
        PasswordUtils.init(passwordEncoder);
    }

    private void initAccounts() {
        Account lord = accountRepository.findAccountByAccountId("lord");
        if (lord == null) {
            lord = Account.builder()
                    .accountId("lord")
                    .password(passwordEncoder.encode("Thaoluan|123@"))
                    .emailAddress("duvu@gpshandle.com")
                    .phoneNumber("(+84) 934580599")
                    .firstName("Vu")
                    .lastName("Du")
                    .privilege(Roles.SUPER)
                    .status(AccountStatus.ACTIVATED)
                    .build();
            accountRepository.save(lord);
        }
    }
}
