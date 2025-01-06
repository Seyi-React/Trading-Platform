package com.oluwaseyi.crypto.tradingplatform.trading_platform.controller;


import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.AuthenticationRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request.ForgotPasswordRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request.RegisterRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request.ResetPasswordRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.response.AuthenticationResponse;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.entity.User;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.AuthenticationException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.InvalidOtpException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.UserAlreadyExistException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/auth")
@Tag(name = "User Management")
@Slf4j
public class UserController {


    @Autowired
    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response =  authenticationService.registerUser(request);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }catch (UserAlreadyExistException ex) {
           return new ResponseEntity<>("User Already Exist",HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")

    public ResponseEntity<?> login( @RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);

        } catch (  AuthenticationException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            authenticationService.initiatePasswordReset(request.getEmail());
            return ResponseEntity.ok(Map.of("message", "OTP sent to your email"));
        } catch (Exception e) {
//            log.error("Error in forgot password", e);
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to process forgot password request"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            authenticationService.resetPassword(request);
            return ResponseEntity.ok(Map.of("message", "Password reset successful"));
        } catch (InvalidOtpException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
//            log.error("Error in reset password", e);
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to reset password"));
        }
    }

    @GetMapping("users")
    public ResponseEntity<List<?>> findAll(){
       List<User> users = authenticationService.findAllUsers();
        try {
            return ResponseEntity.ok(users);
        } catch (Exception e){
            System.out.println("error fetching users");
        }
        return null;
    }


}
