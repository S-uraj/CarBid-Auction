package com.carbid.auth.authservice.controller;


import com.carbid.auth.authservice.Auth.JwtUtil.JwtService;
import com.carbid.auth.authservice.Exception.ApplicationException;
import com.carbid.auth.authservice.serviceimpl.UserDataServiceImpl;
import com.carbid.auth.authservice.entity.UserData;
import com.carbid.auth.authservice.models.AuthRequest;
import com.carbid.auth.authservice.models.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/login")
@Slf4j
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDataServiceImpl userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity authenticateUser(@RequestBody AuthRequest authRequest) {
        log.info("Inside Login - authenticate user function");
        authenticate(authRequest.getUserName(), authRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUserName());
        UserData authUser = userService.getUserByUserName(authRequest.getUserName());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", authUser.getRole().toString());
        String jwt = jwtService.generateToken(claims, userDetails);
        return new ResponseEntity(
                new AuthResponse(authUser.getName(), authUser.getLastName(),
                        authUser.getEmail(), jwt), HttpStatus.OK);
    }

    private void authenticate(String email, String password) {
        log.info("Inside authenticateManager - authenticate function");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            throw new ApplicationException("Bad-Credentials", "Incorrect UserName/Password", HttpStatus.FORBIDDEN);
        }
    }
}
