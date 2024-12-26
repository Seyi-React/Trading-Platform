package com.oluwaseyi.crypto.tradingplatform.trading_platform.service;

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
    private Map<String, OtpData> otpMap = new ConcurrentHashMap<>();

    @Value("${otp.validity.minutes:5}")
    private int otpValidityMinutes;

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
                        .minusMinutes(otpValidityMinutes)
                        .isBefore(otpData.getCreatedAt());

        if (isValid) {
            otpMap.remove(email); // Remove OTP after successful validation
        }

        return isValid;
    }

    @Data
    @AllArgsConstructor
    private static class OtpData {
        private String otp;
        private LocalDateTime createdAt;
    }
}
