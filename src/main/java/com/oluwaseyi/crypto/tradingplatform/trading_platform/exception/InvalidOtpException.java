package com.oluwaseyi.crypto.tradingplatform.trading_platform.exception;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String invalidOrExpiredOtp) {
        super(invalidOrExpiredOtp);
    }
}
