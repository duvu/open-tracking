package me.duvu.tracking.jobs;

import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.enumeration.AccountStatus;
import me.duvu.tracking.domain.enumeration.Roles;
import me.duvu.tracking.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
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

//        Account sysadmin = accountRepository.findAccountByAccountId("sysadmin");
//        if (sysadmin == null) {
//            sysadmin = Account.builder()
//                    .accountId("sysadmin")
//                    .password(passwordEncoder.encode("123456"))
//                    .emailAddress("admin1@vd5.com")
//                    .phoneNumber("(+84) 934580599")
//                    .firstName("Vu")
//                    .lastName("Du")
//                    //.company(companyRepository.findByName("Zealand"))
//                    //.privileges(new HashSet<Privilege>(Arrays.asList(privilegeRepository.findByName(RolesList.ROLE_SYSTEM_ADMIN))))
//                    .privilege(Roles.SYSADMIN)
//                    .status(AccountStatus.ACTIVATED)
//                    .build();
//            accountRepository.save(sysadmin);
//        }
//
//        Account companyAdmin = accountRepository.findAccountByAccountId("company");
//        if (companyAdmin == null) {
//            companyAdmin = Account.builder()
//                    .accountId("company")
//                    .password(passwordEncoder.encode("123456"))
//                    .emailAddress("admin2@vd5.com")
//                    .phoneNumber("(+84) 934580599")
//                    .firstName("Vu")
//                    .lastName("Du")
//                    //.privileges(new HashSet<Privilege>(Arrays.asList(privilegeRepository.findByName(RolesList.ROLE_COMPANY_ADMIN))))
//                    .privilege(Roles.ADMIN)
//                    .status(AccountStatus.ACTIVATED)
//                    .build();
//            accountRepository.save(companyAdmin);
//        }
//
//        Account companyMod = accountRepository.findAccountByAccountId("moderator");
//        if (companyMod == null) {
//            companyMod = Account.builder()
//                    .accountId("moderator")
//                    .password(passwordEncoder.encode("123456"))
//                    .emailAddress("admin3@vd5.com")
//                    .phoneNumber("(+84) 934580599")
//                    .firstName("Vu")
//                    .lastName("Du")
//                    //.privileges(new HashSet<Privilege>(Arrays.asList(privilegeRepository.findByName(RolesList.ROLE_COMPANY_MOD))))
//                    .privilege(Roles.MODERATOR)
//                    .status(AccountStatus.ACTIVATED)
//                    .build();
//            accountRepository.save(companyMod);
//        }
//
//
//        Account companyUser = accountRepository.findAccountByAccountId("user");
//        if (companyUser == null) {
//            companyUser = Account.builder()
//                    .accountId("user")
//                    .password(passwordEncoder.encode("123456"))
//                    .emailAddress("admin4@vd5.com")
//                    .phoneNumber("(+21) 908493843")
//                    .firstName("Arthur")
//                    .lastName("Arthur")
//                    //.privileges(new HashSet<>(Arrays.asList(privilegeRepository.findByName(RolesList.ROLE_COMPANY_USER))))
//                    .privilege(Roles.NORMAL_USER)
//                    .status(AccountStatus.ACTIVATED)
//                    .build();
//            accountRepository.save(companyUser);
//        }
    }
}
