package me.duvu.tracking.services;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.SmtpProperties;
import me.duvu.tracking.domain.enumeration.AccountStatus;
import me.duvu.tracking.domain.enumeration.Roles;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.exception.ObjectNotFoundException;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.repository.SmtpPropertiesRepository;
import me.duvu.tracking.specification.AccountSpecification;
import me.duvu.tracking.utils.PasswordUtils;
import me.duvu.tracking.web.rest.model.request.AccountRequest;
import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.web.rest.model.request.SmtpPropertiesModel;
import me.duvu.tracking.web.rest.model.request.SmtpPropertiesRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author beou on 8/1/17 04:55
 * @version 1.0
 */
@Slf4j
@Service
public class AccountService extends AbstractService<Account, AccountRequest> {

    private final AccountRepository accountRepository;
    private final SmtpPropertiesRepository smtpPropertiesRepository;
    private final AccountSpecification specificationHelper;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          SmtpPropertiesRepository smtpPropertiesRepository,
                          AccountSpecification specificationHelper) {

        this.accountRepository = accountRepository;
        this.smtpPropertiesRepository = smtpPropertiesRepository;
        this.specificationHelper = specificationHelper;
    }

    @Override
    public Page<Account> getAll(String search, Pageable pageable) {
        Specification<Account> specification = specificationHelper.search(search);
        return accountRepository.findAll(specification, pageable);
    }

    @Override
    public List<Account> getAll(String search) {
        Specification<Account> specification = specificationHelper.search(search);
        return accountRepository.findAll(specification);
    }

    @Override
    public Account getById(Long id) {
        Specification<Account> specification = specificationHelper.findOne(id);
        return accountRepository.findOne(specification).orElse(null);
    }

    public Account getMe() {
        Long accountId = ApplicationContext.getAccountId();
        if (accountId != null && accountId > 0) {
            return getById(accountId);
        } else {
            throw new ObjectNotFoundException("You are not logged in");
        }
    }

    @Override
    @Transactional
    public Account create(AccountRequest request) {
        String tzStr = StringUtils.isEmpty(request.getTimeZoneStr()) ? "UTC" : request.getTimeZoneStr();
        String lang = StringUtils.isEmpty(request.getLanguage()) ? "EN" : request.getLanguage();
        AccountStatus status = request.getStatus();
        Roles ordinal = request.getPrivilege();

        Account currentAccount = accountRepository.findByAccountId(ApplicationContext.getCurrentUserName()).orElse(null);
        //-- default is USER.
        ordinal = ordinal != null ? ordinal : Roles.NORMAL_USER;
        String passwd = StringUtils.isNotBlank(request.getPassword()) ? request.getPassword() : PasswordUtils.getRandom();
        String passwordEncorded = PasswordUtils.encode(passwd);

        Set<Long> smtpPropertiesIds = request.getSmtpPropertiesIds();
        Set<SmtpProperties> smtpProperties = smtpPropertiesIds != null ? smtpPropertiesIds.stream().map(x -> smtpPropertiesRepository.findById(x).orElse(null)).collect(Collectors.toSet()) : null;

        Account account = Account.builder()
                .accountId(request.getAccountId())
                .password(passwordEncorded)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(status)
                .timeZoneStr(tzStr)
                .language(lang)
                .privilege(ordinal)
                .manager(currentAccount)
                .phoneNumber(request.getPhoneNumber())
                .photoUrl(request.getPhotoUrl())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .emailAddress(request.getEmailAddress())
                .smtpProperties(smtpProperties)
                .createdBy(ApplicationContext.getCurrentUserName())
                .build();
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account update(Long id, AccountRequest request) {
        Account account = accountRepository.getOne(id);
        String tzStr = StringUtils.isEmpty(request.getTimeZoneStr()) ? "UTC" : request.getTimeZoneStr();
        String lang = StringUtils.isEmpty(request.getLanguage()) ? "EN" : request.getLanguage();

        account.setAccountId(request.getAccountId());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());

        Set<Long> smtpPropertiesIds = request.getSmtpPropertiesIds();
        Set<SmtpProperties> smtpProperties = smtpPropertiesIds != null ? smtpPropertiesIds.stream().map(x -> smtpPropertiesRepository.findById(x).orElse(null)).collect(Collectors.toSet()) : null;

        account.setSmtpProperties(smtpProperties);

        String password = request.getPassword();
        if (StringUtils.isNotBlank(password)) {
            String passwordEncorded = PasswordUtils.encode(password);
            account.setPassword(passwordEncorded);
        }

        Roles ordinal = request.getPrivilege();
        account.setPrivilege(ordinal);
        AccountStatus status = request.getStatus();

        account.setStatus(status);
        account.setTimeZoneStr(tzStr);
        account.setLanguage(lang);

        account.setPhoneNumber(request.getPhoneNumber());
        account.setPhotoUrl(request.getPhotoUrl());
        account.setAddressLine1(request.getAddressLine1());
        account.setAddressLine2(request.getAddressLine2());
        account.setEmailAddress(request.getEmailAddress());
        account.setUpdatedBy(ApplicationContext.getCurrentUserName());
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Specification<Account> specification = specificationHelper.findOne(id);
        Account account = accountRepository.findOne(specification).orElse(null);
        if (account != null) {
            accountRepository.delete(account);
        } else {
            throw new AccessDeninedOrNotExisted("You cannot delete this account #id:" + id);
        }
    }

    public List<SmtpProperties> getAllSmtpProperties(Long accountId) {
        Account account = getById(accountId);
        if (ApplicationContext.checkManager(account)) {
            return new ArrayList<>(account.getSmtpProperties());
        } else {
            throw new AccessDeninedOrNotExisted("Account not existed or access denied!");
        }
    }

    public void addSmtpToAccount(Long accountId, Long smtpId) {
        Account account = getById(accountId);
        Account currentAccount = ApplicationContext.getAccount();
    }

    public SmtpProperties addNewSmtToAccount(Long accountId, SmtpPropertiesRequest request) {
        Account account = getById(accountId);
        if (ApplicationContext.checkManager(account)) {
            // 1. create new SmtpProperties
            SmtpProperties smtpProperties = SmtpProperties.builder()
                    .host(request.getHost())
                    .port(request.getPort())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .startTls(request.getStartTls())
                    .auth(request.getAuth())
                    .protocol(request.getProtocol())
                    .maxSizeAttachment(request.getMaxSizeAttachment())
                    .createdBy(ApplicationContext.getCurrentUserName())
                    .updatedBy(ApplicationContext.getCurrentUserName())
                    .build();
            smtpPropertiesRepository.save(smtpProperties);
            // 2. setSmtpProperties to account
            Set<SmtpProperties> smtpPropertiesSet = account.getSmtpProperties(); // new HashSet<>();
            if (smtpPropertiesSet == null) {
                smtpPropertiesSet = new HashSet<>();
            }
            smtpPropertiesSet.add(smtpProperties);
            account.setSmtpProperties(smtpPropertiesSet);

            accountRepository.save(account);
            return smtpProperties;
        } else {
            throw new AccessDeninedOrNotExisted("Account not existed or access denied!");
        }
    }
}