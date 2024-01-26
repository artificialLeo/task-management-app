package com.taskmanagement.app.repository;

import com.taskmanagement.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    private User newUser;

    @BeforeEach
    public void setUp() {
        newUser = new User();
        newUser.setUsername("username");
        newUser.setEmail("test@example.com");
        newUser.setFirstName("firstName");
        newUser.setLastName("lastName");
        newUser.setPassword("password");
        userRepository.save(newUser);
    }

    @Test
    @DisplayName("findUserByEmail -> String email equals")
    @Transactional
    @Rollback
    public void findUserByEmail_WhenExistingEmailPassed_ReturnExpectedMail() {
        User existentUser = userRepository.findUserByEmail("test@example.com");

        String expected = existentUser.getEmail();
        String actual = newUser.getEmail();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("findUserByEmail -> String password equals")
    @Transactional
    @Rollback
    public void findUserByEmail_WhenExistingEmailPassed_ReturnEqualPasswords() {
        User existentUser = userRepository.findUserByEmail("test@example.com");

        String expected = existentUser.getPassword();
        String actual = newUser.getPassword();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("existsByEmail -> Boolean true")
    @Transactional
    @Rollback
    public void existsByEmail_existsByEmail_ReturnTrue() {
        boolean userExists = userRepository.existsByEmail("test@example.com");

        Assertions.assertTrue(userExists);
    }

}
