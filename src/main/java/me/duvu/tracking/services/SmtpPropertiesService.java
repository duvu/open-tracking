package me.duvu.tracking.services;

import lombok.extern.slf4j.Slf4j;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.SmtpProperties;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.repository.SmtpPropertiesRepository;
import me.duvu.tracking.specification.SmtpPropertiesSpecification;
import me.duvu.tracking.web.rest.model.request.SmtpPropertiesRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author beou on 12/7/19 23:22
 */

@Slf4j
@Service
public class SmtpPropertiesService extends AbstractService<SmtpProperties, SmtpPropertiesRequest>{

    private final SmtpPropertiesRepository smtpPropertiesRepository;
    private final AccountRepository accountRepository;

    public SmtpPropertiesService(SmtpPropertiesRepository smtpPropertiesRepository, AccountRepository accountRepository) {
        this.smtpPropertiesRepository = smtpPropertiesRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    Page<SmtpProperties> getAll(String search, Pageable pageable) {
        return null;
    }

    @Override
    List<SmtpProperties> getAll(String search) {
        return null;
    }

    List<SmtpProperties> getAllByAccountId(Long accountId) {
        Account account = accountRepository.getOne(accountId);
        return new ArrayList<>(account.getSmtpProperties());
    }

    @Override
    SmtpProperties getById(Long id) {
        return smtpPropertiesRepository.findById(id).orElse(null);
    }

    @Override
    public SmtpProperties create(SmtpPropertiesRequest request) {
        SmtpProperties smtpProperties = SmtpProperties.builder()
                .protocol(request.getProtocol())
                .host(request.getHost())
                .port(request.getPort())
                .username(request.getUsername())
                .password(request.getPassword())
                .auth(request.getAuth())
                .startTls(request.getStartTls())
                .maxSizeAttachment(request.getMaxSizeAttachment())
                .build();

        return smtpPropertiesRepository.save(smtpProperties);
    }

    @Override
    SmtpProperties update(Long id, SmtpPropertiesRequest request) {
        SmtpProperties smtpProperties = smtpPropertiesRepository.findById(id).orElse(null);
        if (smtpProperties != null) {
            if (StringUtils.isNotBlank(request.getProtocol())) {
                smtpProperties.setProtocol(request.getProtocol());
            }

            if (StringUtils.isNotBlank(request.getHost())) {
                smtpProperties.setHost(request.getHost());
            }

            if (request.getPort() != null && request.getPort() != 0) {
                smtpProperties.setPort(request.getPort());
            }

            if (StringUtils.isNotBlank(request.getUsername())) {
                smtpProperties.setUsername(request.getUsername());
            }

            if (StringUtils.isNotBlank(request.getPassword())) {
                smtpProperties.setPassword(request.getPassword());
            }

            if (request.getAuth() != null) {
                smtpProperties.setAuth(request.getAuth());
            }

            if (request.getStartTls() != null) {
                smtpProperties.setStartTls(request.getStartTls());
            }

            if (request.getMaxSizeAttachment() != null) {
                smtpProperties.setMaxSizeAttachment(request.getMaxSizeAttachment());
            }
            return smtpPropertiesRepository.save(smtpProperties);
        } else {
            throw new RuntimeException("Not found any SmtpProperties");
        }

    }

    @Override
    void delete(Long id) {
        SmtpProperties smtpProperties = smtpPropertiesRepository.findById(id).orElse(null);
        if (smtpProperties != null) {
            smtpPropertiesRepository.delete(smtpProperties);
        } else {
            throw new RuntimeException("Object Not Found #" + id);
        }
    }
}
