package com.oluwaseyi.crypto.tradingplatform.trading_platform.exception;

public class AuthenticationException extends Throwable {
    public AuthenticationException(String invalidEmailOrPassword) {
        super(invalidEmailOrPassword);
    }
}
