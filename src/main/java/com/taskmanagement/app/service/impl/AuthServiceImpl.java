package com.taskmanagement.app.service.impl;

import com.taskmanagement.app.dto.AuthedRes;
import com.taskmanagement.app.dto.LoginReq;
import com.taskmanagement.app.dto.RegisterReq;
import com.taskmanagement.app.exception.ErrorRes;
import com.taskmanagement.app.model.User;
import com.taskmanagement.app.security.JwtUtil;
import com.taskmanagement.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userServiceImpl;

    public ResponseEntity<AuthedRes> authenticateUser(LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword())
        );

        String email = authentication.getName();
        User user = new User();
        user.setEmail(email);
        user.setPassword("");

        String token = jwtUtil.createToken(user);

        com.taskmanagement.app.dto.AuthedRes authedRes
                = new com.taskmanagement.app.dto.AuthedRes(email, token);
        return ResponseEntity.ok(authedRes);
    }

    public ResponseEntity<AuthedRes> registerUser(RegisterReq registerReq) {
        if (userServiceImpl.existsByEmail(registerReq.getEmail())) {
            ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorRes(HttpStatus.BAD_REQUEST, "Email already registered"));
        }

        User newUser = new User();
        newUser.setEmail(registerReq.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        newUser.setFirstName(registerReq.getFirstName());
        newUser.setLastName(registerReq.getLastName());

        userServiceImpl.save(newUser);

        String token = jwtUtil.createToken(newUser);
        AuthedRes authedRes = new AuthedRes(newUser.getEmail(), token);

        return ResponseEntity.ok(authedRes);
    }

}
