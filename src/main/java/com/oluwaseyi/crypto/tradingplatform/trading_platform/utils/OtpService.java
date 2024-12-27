package com.oluwaseyi.crypto.tradingplatform.trading_platform.utils;

import com.oluwaseyi.crypto.tradingplatform.trading_platform.config.AppConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final Map<String, OtpData> otpMap = new ConcurrentHashMap<>();
    private final AppConfig appConfig;

    public OtpService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void saveOTP(String email, String otp) {
        otpMap.put(email, new OtpData(otp, LocalDateTime.now()));
    }

    public boolean validateOTP(String email, String otp) {
        OtpData otpData = otpMap.get(email);
        if (otpData == null) {
            return false;
        }

        boolean isValid = otpData.getOtp().equals(otp) &&
                LocalDateTime.now()
                        .minusMinutes(appConfig.getOtp().getValidityMinutes())
                        .isBefore(otpData.getCreatedAt());

        if (isValid) {
            otpMap.remove(email);
        }

        return isValid;
    }

    private static class OtpData {
        private String otp;
        private LocalDateTime createdAt;

        public OtpData(String otp, LocalDateTime createdAt) {
            this.otp = otp;
            this.createdAt = createdAt;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }
}