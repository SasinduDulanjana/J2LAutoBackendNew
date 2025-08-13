package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.UserRequest;
import com.example.smartPos.controllers.responses.UserResponse;
import com.example.smartPos.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Integer id, @RequestBody UserRequest user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}
