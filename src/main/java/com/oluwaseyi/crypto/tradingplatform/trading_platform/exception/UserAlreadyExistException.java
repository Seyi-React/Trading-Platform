package com.oluwaseyi.crypto.tradingplatform.trading_platform.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String s) {
        super(s);
    }
}
