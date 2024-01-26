package com.taskmanagement.app.service.impl;

import com.taskmanagement.app.model.User;
import com.taskmanagement.app.repository.UserRepository;
import com.taskmanagement.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User newUser) {
        userRepository.save(newUser);
    }
}
