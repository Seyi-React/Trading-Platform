package com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class ForgotPasswordRequest {
    @Email
    @NotBlank
    private String email;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }

    public ForgotPasswordRequest() {
    }
}
