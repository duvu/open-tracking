package me.duvu.tracking.external.email;

import me.duvu.tracking.utils.ZQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author beou on 5/17/18 00:29
 */

@Service
public class MailService {
    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender javaMailSender;

    private final ZQueue<Object> mailQueue = new ZQueue<>();

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(JavaMailSender javaMailSender, MessageSource messageSource,
                       SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom("jHipsterProperties.getMail().getFrom()");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    public void send(String to, String subject, String text) {
        send(to, null, subject, text);
    }

    public void send(String to, String cc, String subject, String text) {
        send(to, cc, subject, text, null);
    }

    public void send(String to, String cc, String subject, String text, String pathToAttach) {
        String toAddress = StringUtils.trim(to);
        String ccAddress = StringUtils.trim(cc);
        String subjectText = StringUtils.trim(subject);
        String textText = StringUtils.trim(text);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            if (StringUtils.isNotBlank(toAddress)) {
                messageHelper.setTo(toAddress);
            }
            if (StringUtils.isNotBlank(ccAddress)) {
                messageHelper.setCc(ccAddress);
            }
            messageHelper.setSubject(subjectText);
            messageHelper.setText(textText, true);
        };
        mailQueue.add(messagePreparator);
    }

    // delay 5s and run interval 10s
    @Scheduled(initialDelay = 5003, fixedRate = 10000)
    public void scheduleFixedRateTask() {
        List<Object> sendingMails = mailQueue.poll(30); // 30 mails in 10s
        sendingMails.forEach((message -> {
            if (message instanceof SimpleMailMessage) {
                javaMailSender.send((SimpleMailMessage) message);
            } else if (message instanceof MimeMessage) {
                javaMailSender.send((MimeMessage) message);
            } else if (message instanceof MimeMessagePreparator) {
                javaMailSender.send((MimeMessagePreparator) message);
            }
        }));
    }
}
