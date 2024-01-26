package com.taskmanagement.app.controller;

import com.taskmanagement.app.dto.AuthedRes;
import com.taskmanagement.app.dto.LoginReq;
import com.taskmanagement.app.dto.RegisterReq;
import com.taskmanagement.app.security.JwtUtil;
import com.taskmanagement.app.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<AuthedRes> login(@RequestBody LoginReq loginReq) {
        return authServiceImpl.authenticateUser(loginReq);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthedRes> register(@RequestBody RegisterReq registerReq) {
        return authServiceImpl.registerUser(registerReq);
    }
}
