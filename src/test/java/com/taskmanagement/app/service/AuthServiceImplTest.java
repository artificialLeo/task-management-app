package com.taskmanagement.app.service;

import com.taskmanagement.app.dto.AuthedRes;
import com.taskmanagement.app.dto.LoginReq;
import com.taskmanagement.app.dto.RegisterReq;
import com.taskmanagement.app.security.JwtUtil;
import com.taskmanagement.app.service.impl.AuthServiceImpl;
import com.taskmanagement.app.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private AuthServiceImpl authServiceImpl;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("authenticateUser -> Valid token")
    void authenticateUser_ValidCredentials_ReturnsToken() {
        LoginReq validLoginReq = new LoginReq("valid@example.com", "validPassword");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(validLoginReq.getEmail());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.createToken(any())).thenReturn("mockedToken");

        ResponseEntity<AuthedRes> response = authServiceImpl.authenticateUser(validLoginReq);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
    }

    @Test
    @DisplayName("registerUser -> Valid token")
    void registerUser_ValidRegistration_ReturnsToken() {
        RegisterReq validRegisterReq = new RegisterReq("newuser@example.com", "password", "firstName", "lastName");
        when(userServiceImpl.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(jwtUtil.createToken(any())).thenReturn("mockedToken");

        ResponseEntity<AuthedRes> response = authServiceImpl.registerUser(validRegisterReq);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getToken());
    }
}
