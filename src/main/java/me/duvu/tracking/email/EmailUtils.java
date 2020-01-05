package me.duvu.tracking.email;

import me.duvu.tracking.domain.SmtpProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.Properties;

public class EmailUtils {
    public static JavaMailSender getJavaMailSender(SmtpProperties smtpProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(smtpProperties.getHost());
        mailSender.setPort(smtpProperties.getPort());
        mailSender.setUsername(smtpProperties.getUsername());
        mailSender.setPassword(smtpProperties.getPassword());

        mailSender.setProtocol(smtpProperties.getProtocol());
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        if (smtpProperties.getAuth()) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
        if (smtpProperties.getStartTls()) {
            props.put("mail.smtp.starttls.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "false");
        }

        props.put("mail.debug", "true");

        return mailSender;
    }

    public static void sendEmail(SmtpProperties smtp, String to, String cc, String subject, String body) {
        JavaMailSender mailSender = getJavaMailSender(smtp);
        String toAddress = StringUtils.trim(to);
        String ccAddress = StringUtils.trim(cc);
        String subjectText = StringUtils.trim(subject);
        String textText = StringUtils.trim(body);

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
        mailSender.send(messagePreparator);
    }
}
