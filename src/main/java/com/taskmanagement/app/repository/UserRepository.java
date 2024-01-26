package com.taskmanagement.app.repository;

import com.taskmanagement.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    boolean existsByEmail(String email);
}
