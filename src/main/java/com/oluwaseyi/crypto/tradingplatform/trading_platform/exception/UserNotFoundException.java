package com.oluwaseyi.crypto.tradingplatform.trading_platform.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
