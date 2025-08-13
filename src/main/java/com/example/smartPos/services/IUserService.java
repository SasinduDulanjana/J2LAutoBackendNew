package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.UserRequest;
import com.example.smartPos.controllers.responses.UserResponse;
import com.example.smartPos.repositories.model.User;

import java.util.List;

public interface IUserService {

    List<UserResponse> getAllUsers();

    UserResponse getUser(Integer id);

    UserResponse createUser(UserRequest user);

    UserResponse updateUser(Integer id, UserRequest user);

    void deleteUser(Integer id);

    UserResponse getUserByUsername(String username);

    Boolean existsByUsername(String username);

}
