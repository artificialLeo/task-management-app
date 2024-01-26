package com.taskmanagement.app.service;

import com.taskmanagement.app.dto.AuthedRes;
import com.taskmanagement.app.dto.LoginReq;
import com.taskmanagement.app.dto.RegisterReq;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthedRes> authenticateUser(LoginReq loginReq);

    ResponseEntity<AuthedRes> registerUser(RegisterReq registerReq);
}
