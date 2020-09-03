package me.duvu.tracking.jobs;

import me.duvu.tracking.entities.Account;
import me.duvu.tracking.entities.Device;
import me.duvu.tracking.entities.MailProperties;
import me.duvu.tracking.external.email.EmailService;
import me.duvu.tracking.external.email.EmailUtils;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.services.DeviceService;
import me.duvu.tracking.services.EventDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

/**
 * @author beou on 11/4/18 18:45
 */

@Slf4j
@Component
@Profile("prod")
public class DailyTask {
    @Value("${vd5.sysadmin.email}")
    private String sysadminEmail;

    private final TemplateEngine templateEngine;
    private final DeviceService deviceService;
    private final EventDataService eventDataService;
    private final AccountRepository accountRepository;

    private final EmailService emailService;

    public DailyTask(TemplateEngine templateEngine,
                     DeviceService deviceService,
                     EventDataService eventDataService, AccountRepository accountRepository,
                     EmailService emailService) {
        this.templateEngine = templateEngine;
        this.deviceService = deviceService;
        this.eventDataService = eventDataService;
        this.accountRepository = accountRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron="0 0 9 * * *", zone="Asia/Saigon") //9 o'clock of every day.
    public void deviceReportDaily() {
        List<Account> accountList = accountRepository.findAll();
        accountList.forEach(account -> {
            MailProperties mailProperties = account.getMailProperties();
            String emailTo = account.getEmailAddress();
            List<Device> deviceList = deviceService.getAllDeviceByAccountId(account.getId());
            Context ctx = new Context();
            ctx.setVariable("deviceList", deviceList);
            ctx.setVariable("account", account);
            String dailyReportDeviceList = templateEngine.process("dailyDeviceReportList", ctx);
            EmailUtils.sendEmail(mailProperties, emailTo, sysadminEmail, "Daily Device List", dailyReportDeviceList);
        });
    }


    // cleanup disabled devices and its eventdata
//    @Scheduled(cron="0 0 6 * * *", zone="Asia/Saigon") //6 o'clock of every day.
    @Scheduled(fixedRate = 60 * 1000)
    public void cleanupDatabase() {
        List<Device> deviceList = deviceService.getAllDisabled();
         log.info("... Cleaning database: " + deviceList.size());
        if (deviceList.size() > 0) {
            deviceList.forEach(eventDataService::purgeByDevice);
            deviceService.delete(deviceList);
        }
    }

}

