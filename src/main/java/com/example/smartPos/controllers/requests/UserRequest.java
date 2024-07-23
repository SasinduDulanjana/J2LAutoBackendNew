package com.example.smartPos.controllers.requests;

import lombok.Data;

@Data
public class UserRequest {
    private String userId;
    private String userName;
    private String password;
    private String role;
}
