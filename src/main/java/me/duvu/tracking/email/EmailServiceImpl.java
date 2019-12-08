package me.duvu.tracking.email;

import me.duvu.tracking.utils.ZQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @author beou on 5/17/18 00:28
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final ZQueue<Object> mailQueue = new ZQueue<>();

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String text) {
        send(to, null, subject, text);
    }

    @Override
    public void send(String to, String cc, String subject, String text) {
        send(to, cc, subject, text, null);
    }

    @Override
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
                mailSender.send((SimpleMailMessage) message);
            } else if (message instanceof MimeMessage) {
                mailSender.send((MimeMessage) message);
            } else if (message instanceof MimeMessagePreparator) {
                mailSender.send((MimeMessagePreparator) message);
            }
        }));
    }
}
