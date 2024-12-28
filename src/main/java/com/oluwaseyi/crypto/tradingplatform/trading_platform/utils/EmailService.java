package com.oluwaseyi.crypto.tradingplatform.trading_platform.utils;

import com.oluwaseyi.crypto.tradingplatform.trading_platform.config.AppConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;
    private final AppConfig appConfig;

    public EmailService(JavaMailSender emailSender, AppConfig appConfig) {
        this.emailSender = emailSender;
        this.appConfig = appConfig;
    }

    public void sendOtpEmail(String email, String otp) {
        try {
            System.out.println("Starting email send process...");
            System.out.println("From email: " + appConfig.getMail().getFromEmail());
            System.out.println("To email: " + email);

            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(appConfig.getMail().getFromEmail());
            helper.setTo(email);
            helper.setSubject("Password Reset OTP");
            helper.setText("Your OTP for password reset is: " + otp);

            System.out.println("Email configured, attempting to send...");
            emailSender.send(message);
            System.out.println("Email sent successfully");

        } catch (MessagingException e) {
            System.out.println("Failed to send OTP email. Error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP: " + e.getMessage());
        }
    }
}