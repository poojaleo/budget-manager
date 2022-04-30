package com.java.expensetracker.controller;


import com.java.expensetracker.InvalidUserException;
import com.java.expensetracker.request.LoginRequest;
import com.java.expensetracker.response.LoginResponse;
import com.java.expensetracker.security.jwt.JwtTokenProvider;
import com.java.expensetracker.security.service.UserDetailsImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsImplementation userDetailsImplementationService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsImplementation userDetailsImplementationService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsImplementationService = userDetailsImplementationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) throws Exception {

        try {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException badCredentialsException) {
                throw new InvalidUserException("Incorrect Username or password", badCredentialsException);
            }

            UserDetails userDetails = userDetailsImplementationService.loadUserByUsername(request.getUsername());
            String jwt = jwtTokenProvider.generateToken(userDetails);

            return new ResponseEntity<>(new LoginResponse(jwt), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
