package me.duvu.tracking.services;

import me.duvu.tracking.entities.Account;
import me.duvu.tracking.entities.enumeration.AccountStatus;
import me.duvu.tracking.entities.enumeration.Roles;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.utils.RandomUtil;
import me.duvu.tracking.web.rest.model.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author beou on 1/29/19 01:33
 */
@Slf4j
@Service
@Transactional
public class HomeService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheManager cacheManager;
    public HomeService(AccountRepository accountRepository,
                       PasswordEncoder passwordEncoder,
                       CacheManager cacheManager) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.cacheManager = cacheManager;
    }

    public Account register(RegisterRequest request) {


        String tzStr = StringUtils.isEmpty(request.getTimeZoneStr()) ? "UTC" : request.getTimeZoneStr();
        String lang = StringUtils.isEmpty(request.getLanguage()) ? "EN" : request.getLanguage();

        Roles ordinal = Roles.ADMIN;
        //-- default is USER.
        Account account = Account.builder()
                .accountId(request.getAccountId())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(AccountStatus.PENDING)
                .activated(false)
                .activationKey(RandomUtil.generateActivationKey())
                .timeZoneStr(tzStr)
                .language(lang)
                .privilege(ordinal)
                .phoneNumber(request.getPhoneNumber())
                .photoUrl(request.getPhotoUrl())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .emailAddress(request.getEmailAddress())
                .build();

        if (!StringUtils.isEmpty(request.getPassword())) {
            account.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        //todo: send email to registering email

        return accountRepository.save(account);
    }

    public Optional<Account> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return accountRepository.findOneByActivationKey(key).map(account -> {
           account.setActivated(true);
           account.setActivationKey(null);
           log.info("Activated account: {}", account);
           return accountRepository.save(account);
        });
    }
}
