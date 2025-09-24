package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.LoginRequest;
import com.example.smartPos.controllers.requests.UserRequest;
import com.example.smartPos.controllers.responses.LoginResponse;
import com.example.smartPos.controllers.responses.UserResponse;
import com.example.smartPos.security.JwtUtil;
import com.example.smartPos.services.IUserService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserController(IUserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Extract roles from the authenticated user
        List<String> roles = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        String token = jwtUtil.generateToken(loginRequest.getUsername(), roles);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setMessage("Login Success");
        return ResponseCreator.success(loginResponse);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    public UserResponse getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @Cacheable(value = "users", key = "#username")
    @GetMapping("/getUserByName/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
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

    @GetMapping("/test")
    public String test() {
        return "Test successful";
    }

}
