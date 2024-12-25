package com.oluwaseyi.crypto.tradingplatform.trading_platform.controller;


import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.AuthenticationRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request.RegisterRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.response.AuthenticationResponse;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.AuthenticationException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.UserAlreadyExistException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/auth")
@Tag(name = "User Management")
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


}
