package com.oluwaseyi.crypto.tradingplatform.trading_platform.service;


import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.AuthenticationRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.request.RegisterRequest;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.DTO.response.AuthenticationResponse;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.config.JwtService;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.entity.Role;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.entity.User;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.AuthenticationException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.exception.UserAlreadyExistException;
import com.oluwaseyi.crypto.tradingplatform.trading_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

     @Autowired
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public AuthenticationResponse registerUser(RegisterRequest register) throws Exception {


        if (userRepository.existsByEmail(register.getEmail())) {
            throw new UserAlreadyExistException("User with email " + register.getEmail() + " already exists");
        }

      try{

          User createdUser = new User();
          createdUser.setPassword(passwordEncoder.encode(register.getPassword()));
          createdUser.setRole(Role.USER);
          createdUser.setEmail(register.getEmail());
          createdUser.setFullName(register.getFullName());

          userRepository.save(createdUser);

          AuthenticationResponse createdMessage = new AuthenticationResponse();

          createdMessage.setMessage("User Successfully created");

          return createdMessage;
      } catch (Exception e) {
          throw new RuntimeException("Problem occurred while creating this user");
      }
  }



    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationException {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // Retrieve the user from the repository
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // Generate the JWT token
            var jwtToken = jwtService.generateToken((UserDetails) user);

            AuthenticationResponse loginResponse = new AuthenticationResponse();


            loginResponse.setMessage("User successfully login");
            loginResponse.setToken(jwtToken);

            // Return a successful response
            return loginResponse;
        } catch (BadCredentialsException e) {
            // Handle incorrect email or password
            throw new AuthenticationException("Invalid email or password");
        }
    }
}

