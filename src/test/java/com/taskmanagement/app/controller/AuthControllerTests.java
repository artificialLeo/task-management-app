package com.taskmanagement.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.app.dto.LoginReq;
import com.taskmanagement.app.dto.RegisterReq;
import com.taskmanagement.app.model.User;
import com.taskmanagement.app.repository.UserRepository;
import com.taskmanagement.app.security.JwtUtil;
import com.taskmanagement.app.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AuthServiceImpl authServiceImpl;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private AuthController authController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(encodedPassword);
        user.setFirstName("firstName");
        user.setLastName("lastName");

        userRepository.save(user);
    }

    @Test
    @DisplayName("login -> Success")
    @Transactional
    @Rollback
    public void login_ValidCredentials_responseMailWithToken() throws Exception {
        LoginReq loginReq = new LoginReq("test@example.com", "password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    @DisplayName("register -> Success")
    @Transactional
    @Rollback
    public void register_ValidData_responseRegisterRequest() throws Exception {
        RegisterReq registerReq = new RegisterReq("newuser@example.com", "password", "firstName", "lastName");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newuser@example.com"))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").isString());
    }

}
