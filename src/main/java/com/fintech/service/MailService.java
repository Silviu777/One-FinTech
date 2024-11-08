package com.fintech.service;

import com.fintech.model.EmailNotification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(EmailNotification email) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("no-reply-signup@onefintech.com");
            messageHelper.setTo(email.getRecipient());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(mailContentBuilder.build(email.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!");

        } catch (MailException e) {
            throw new RuntimeException("Exception occurred when sending email to " + email.getRecipient());
        }
    }
}
