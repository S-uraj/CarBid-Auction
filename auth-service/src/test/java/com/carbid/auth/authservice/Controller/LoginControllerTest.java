package com.carbid.auth.authservice.Controller;

import com.carbid.auth.authservice.Auth.JwtUtil.JwtService;
import com.carbid.auth.authservice.Exception.ApplicationException;
import com.carbid.auth.authservice.controller.LoginController;
import com.carbid.auth.authservice.models.AuthRequest;
import com.carbid.auth.authservice.models.AuthResponse;
import com.carbid.auth.authservice.serviceimpl.UserDataServiceImpl;
import com.carbid.auth.authservice.entity.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDataServiceImpl userService;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateUser() {
        // Arrange
        AuthRequest authRequest = new AuthRequest("testuser", "testpassword");
        UserData userData = new UserData();
        userData.setName("Test");
        userData.setLastName("User");
        userData.setEmail("testuser@example.com");
        userData.setRole("BUYER");

        UserDetails userDetails = new User(authRequest.getUserName(), authRequest.getPassword(), Collections.emptyList());
        when(userService.loadUserByUsername(authRequest.getUserName())).thenReturn(userDetails);
        when(userService.getUserByUserName(authRequest.getUserName())).thenReturn(userData);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userData.getRole().toString());
        String jwtToken = "mocked-jwt-token";
        when(jwtService.generateToken(claims, userDetails)).thenReturn(jwtToken);

        // Act
        ResponseEntity<AuthResponse> responseEntity = loginController.authenticateUser(authRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AuthResponse authResponse = responseEntity.getBody();
        assertEquals("Test", authResponse.getFirstName());
        assertEquals("User", authResponse.getLastName());
        assertEquals("testuser@example.com", authResponse.getEmail());
        assertEquals(jwtToken, authResponse.getToken());
    }


}
