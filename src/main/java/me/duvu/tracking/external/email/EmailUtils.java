package me.duvu.tracking.external.email;

import me.duvu.tracking.entities.SmtpProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import java.util.Properties;

public class EmailUtils {
    public static JavaMailSender getJavaMailSender(SmtpProperties smtpProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(smtpProperties.getSmtpHost());
        mailSender.setPort(smtpProperties.getSmtpPort());
        mailSender.setUsername(smtpProperties.getSmtpUsername());
        mailSender.setPassword(smtpProperties.getSmtpPassword());

        mailSender.setProtocol(smtpProperties.getSmtpProtocol());
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        if (smtpProperties.getSmtpAuth()) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
        if (smtpProperties.getSmtpStartTls()) {
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
