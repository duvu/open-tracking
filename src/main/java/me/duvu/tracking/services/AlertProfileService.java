package me.duvu.tracking.services;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.AlertEventLog;
import me.duvu.tracking.domain.AlertProfile;
import me.duvu.tracking.domain.enumeration.WeekDays;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.repository.AlertEventLogRepository;
import me.duvu.tracking.repository.AlertProfileRepository;
import me.duvu.tracking.specification.AlertEventLogSpecification;
import me.duvu.tracking.specification.AlertProfileSpecification;
import me.duvu.tracking.web.rest.model.request.AlertProfileRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author beou on 8/4/18 16:32
 */

@Slf4j
@Service
public class AlertProfileService extends AbstractService<AlertProfile, AlertProfileRequest> {

    private final AlertProfileRepository alertProfileRepository;
    private final AlertEventLogRepository alertEventLogRepository;
    private final AccountRepository accountRepository;
    private final AlertProfileSpecification alertProfileSpecification;
    private final AlertEventLogSpecification alertEventLogSpecification;


    @Autowired
    public AlertProfileService(AlertProfileRepository alertProfileRepository,
                               AlertEventLogRepository alertEventLogRepository,
                               AlertProfileSpecification alertProfileSpecification,
                               AccountRepository accountRepository,
                               AlertEventLogSpecification alertEventLogSpecification) {

        this.alertProfileRepository = alertProfileRepository;
        this.alertEventLogRepository = alertEventLogRepository;
        this.alertProfileSpecification = alertProfileSpecification;
        this.accountRepository = accountRepository;
        this.alertEventLogSpecification = alertEventLogSpecification;
    }

    @Override
    public Page<AlertProfile> getAll(String search, Pageable pageable) {
        Specification<AlertProfile> specification = alertProfileSpecification.search(search);
        return alertProfileRepository.findAll(specification, pageable);
    }

    @Override
    public List<AlertProfile> getAll(String search) {
        Specification<AlertProfile> specification = alertProfileSpecification.search(search);
        return alertProfileRepository.findAll(specification);
    }

    @Override
    @Transactional
    public AlertProfile getById(Long id) {
        Specification<AlertProfile> specification = alertProfileSpecification.findOne(id);
        Optional<AlertProfile> optAl = alertProfileRepository.findOne(specification);
        return optAl.orElse(null);
    }

    @Override
    @Transactional
    public AlertProfile create(AlertProfileRequest request) {
        Account account = accountRepository.findById(ApplicationContext.getAccountId()).orElse(null);

        WeekDays weekDays = request.getWeekDays();
        log.info("[>_] week-day:" + weekDays.toString());

        AlertProfile alertProfile = AlertProfile.builder()
                .name(request.getName())
                .description(request.getDescription())
                .account(account)
                .type(request.getType())
                .active(request.isActive())
                .speedKph(request.getSpeedKph())
                .zoneId(request.getZoneId())
                .params1(request.getParams1())
                .params2(request.getParams2())

                .weekDays(weekDays)

                .dayTime(request.getDayTime())

                .alertEmail(request.isAlertEmail())
                .alertSms(request.isAlertSms())
                .alertApp(request.isAlertApp())
                .cannedAction(request.getCannedAction())
                .subject(request.getSubject())
                .text(request.getText())
                .templateId(request.getTemplateId())
                .createdBy(ApplicationContext.getCurrentUserName())
                .build();

        return alertProfileRepository.saveAndFlush(alertProfile);
    }

    @Override
    @Transactional
    public AlertProfile update(Long id, AlertProfileRequest request) {
        Specification<AlertProfile> spec = alertProfileSpecification.findOne(id);
        AlertProfile ap = alertProfileRepository.findOne(spec).orElse(null);
        if (ap != null) {

            ap.setType(request.getType());
            ap.setActive(request.isActive());
            ap.setName(request.getName());
            ap.setDescription(request.getDescription());

            ap.setSpeedKph(request.getSpeedKph());
            ap.setZoneId(request.getZoneId());

            ap.setParams1(request.getParams1());
            ap.setParams2(request.getParams2());

            ap.setWeekDays(request.getWeekDays());
            ap.setDayTime(request.getDayTime());

            ap.setAlertApp(request.isAlertApp());
            ap.setAlertEmail(request.isAlertEmail());
            ap.setAlertSms(request.isAlertSms());
            ap.setCannedAction(request.getCannedAction());

            ap.setSubject(request.getSubject());
            ap.setText(request.getText());
            ap.setTemplateId(request.getTemplateId());

            return alertProfileRepository.save(ap);
        } else {
            throw new AccessDeninedOrNotExisted("Alert Profile not existed");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Specification<AlertProfile> specification = alertProfileSpecification.findOne(id);
        AlertProfile alertProfile = alertProfileRepository.findOne(specification).orElse(null);
        if (alertProfile != null) {
            alertProfileRepository.delete(alertProfile);
        } else {
            throw new AccessDeninedOrNotExisted("Cannot delete this alert-profile #id: " + id);
        }
    }

    //-- alert event log
    public Page<AlertEventLog> getAllAlertEventLog(Long id, Long from, Long to, Pageable pageable) {
        Specification<AlertEventLog> spec = alertEventLogSpecification.searchForDeviceBetween(id, from, to);
        return alertEventLogRepository.findAll(spec, pageable);
    }

    public List<AlertEventLog> getAllAlertEventLog() {
        return alertEventLogRepository.findAll();
    }

    public AlertEventLog getAlertEventLogById(Long id) {
        return alertEventLogRepository.findById(id).orElse(null);
    }
}
