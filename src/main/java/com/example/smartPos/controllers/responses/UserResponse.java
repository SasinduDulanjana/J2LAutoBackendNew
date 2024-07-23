package com.example.smartPos.controllers.responses;

import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String userName;
    private String password;
    private String role;
}
