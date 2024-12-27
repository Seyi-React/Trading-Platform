package com.oluwaseyi.crypto.tradingplatform.trading_platform.utils;

import com.oluwaseyi.crypto.tradingplatform.trading_platform.config.AppConfig;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private final JavaMailSender emailSender;
    @Autowired
    private final AppConfig appConfig;

//    @Value("${spring.mail.username}")
//    private String fromEmail;

    public EmailService(JavaMailSender emailSender, AppConfig appConfig) {
        this.emailSender = emailSender;
        this.appConfig = appConfig;
    }

    public void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(appConfig.getMail().getFromEmail());
            helper.setTo(email);
            helper.setSubject("Password Reset OTP");
            helper.setText("Your OTP for password reset is: " + otp);

            emailSender.send(message);
        } catch (MessagingException e) {
//            log.error("Failed to send OTP email", e);
            throw new EmailSendingException("Failed to send OTP");
        }
    }
}
