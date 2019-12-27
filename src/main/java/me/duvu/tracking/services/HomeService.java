package me.duvu.tracking.services;

import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.enumeration.AccountStatus;
import me.duvu.tracking.domain.enumeration.Roles;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.web.rest.model.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author beou on 1/29/19 01:33
 */
@Slf4j
@Service
public class HomeService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
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
                .status(AccountStatus.ACTIVATED)
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

        return accountRepository.save(account);
    }
}
