package com.oluwaseyi.crypto.tradingplatform.trading_platform.exception;

public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String failedToSendOtp) {
        super(failedToSendOtp);
    }
}
