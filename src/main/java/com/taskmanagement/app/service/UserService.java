package com.taskmanagement.app.service;

import com.taskmanagement.app.model.User;

public interface UserService {
    boolean existsByEmail(String email);

    void save(User newUser);
}
