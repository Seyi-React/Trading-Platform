package com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.response;





public class AuthenticationResponse {
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String message) {
        this.message = message;
    }
}
