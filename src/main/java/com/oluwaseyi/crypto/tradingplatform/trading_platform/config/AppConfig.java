package com.oluwaseyi.crypto.tradingplatform.trading_platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private Mail mail = new Mail();
    private Otp otp = new Otp();

    // Getter and Setter
    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Otp getOtp() {
        return otp;
    }

    public void setOtp(Otp otp) {
        this.otp = otp;
    }

    public static class Mail {
        private String fromEmail;

        public String getFromEmail() {
            return fromEmail;
        }

        public void setFromEmail(String fromEmail) {
            this.fromEmail = fromEmail;
        }
    }

    public static class Otp {
        private int validityMinutes = 5; // default value

        public int getValidityMinutes() {
            return validityMinutes;
        }

        public void setValidityMinutes(int validityMinutes) {
            this.validityMinutes = validityMinutes;
        }
    }
}
