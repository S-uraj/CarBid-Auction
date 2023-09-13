package com.carbid.auth.authservice.controller;

import com.carbid.auth.authservice.Auth.JwtUtil.JwtService;
import com.carbid.auth.authservice.serviceimpl.UserDataServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
public class MiscController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDataServiceImpl userDataService;

    @GetMapping("/validateToken")
    public String validateToken(@RequestParam("token") String jwt){
        log.info("Inside validate function",jwt);
        String userName = jwtService.extractUserName(jwt);
        UserDetails user = userDataService.loadUserByUsername(userName);
        boolean isValid = jwtService.isTokenValid(jwt,user);
        if(isValid)
            return jwtService.extractRoles(jwt);
        return "";
    }
}
