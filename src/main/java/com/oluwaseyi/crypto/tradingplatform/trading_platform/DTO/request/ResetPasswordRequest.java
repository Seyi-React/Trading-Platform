package com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String otp;

    @NotBlank
    @Size(min = 8, max = 20)
    private String newPassword;

    public @Email @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getOtp() {
        return otp;
    }

    public void setOtp(@NotBlank String otp) {
        this.otp = otp;
    }

    public @NotBlank @Size(min = 8, max = 20) String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(@NotBlank @Size(min = 8, max = 20) String newPassword) {
        this.newPassword = newPassword;
    }

    public ResetPasswordRequest(String email, String otp, String newPassword) {
        this.email = email;
        this.otp = otp;
        this.newPassword = newPassword;
    }

    public ResetPasswordRequest() {
    }
}
