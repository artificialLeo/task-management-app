package com.taskmanagement.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
