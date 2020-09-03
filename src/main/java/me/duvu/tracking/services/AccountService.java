package me.duvu.tracking.services;

import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.entities.Account;
import me.duvu.tracking.entities.enumeration.AccountStatus;
import me.duvu.tracking.entities.enumeration.Roles;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.exception.ObjectNotFoundException;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.specification.AccountSpecification;
import me.duvu.tracking.utils.PasswordUtils;
import me.duvu.tracking.web.rest.model.request.AccountRequest;
import me.duvu.tracking.web.rest.model.request.ChangePasswdRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author beou on 8/1/17 04:55
 * @version 1.0
 */
@Slf4j
@Service
public class AccountService extends AbstractService<Account, AccountRequest> {

    private final AccountRepository accountRepository;
    private final AccountSpecification specificationHelper;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          AccountSpecification specificationHelper) {

        this.accountRepository = accountRepository;
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
                .mailProperties(request.getMailProperties())
                .createdBy(ApplicationContext.getCurrentUserName())
                .build();
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account update(Long id, AccountRequest request) {
        Account account = accountRepository.findById(id).orElse(null);
        Assert.notNull(account, "Not found account #" + id);

        String tzStr = StringUtils.isEmpty(request.getTimeZoneStr()) ? "UTC" : request.getTimeZoneStr();
        String lang = StringUtils.isEmpty(request.getLanguage()) ? "EN" : request.getLanguage();

        account.setAccountId(request.getAccountId());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());


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

        account.setMailProperties(request.getMailProperties());
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

    public void changePassword(ChangePasswdRequest request) {
        Account account = accountRepository.findById(request.getId()).orElse(null);
        if (account != null) {
            account.setPassword(PasswordUtils.encode(request.getPassword()));
            accountRepository.save(account);
        } else {
            throw new AccessDeninedOrNotExisted("There's some unexpected error, please try again later!");
        }
    }
}