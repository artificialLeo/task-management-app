package com.taskmanagement.app.security;

import com.taskmanagement.app.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User findUserByEmail(String email){
        User user = new User();
        user.setEmail(email);
        user.setPassword("123456");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        return user;
    }
}