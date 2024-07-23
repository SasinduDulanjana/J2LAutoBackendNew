package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.LoginRequest;
import com.example.smartPos.controllers.requests.RegisterRequest;
import com.example.smartPos.controllers.responses.LoginResponse;
import com.example.smartPos.exception.UserNameNotFoundException;
import com.example.smartPos.repositories.RoleRepository;
import com.example.smartPos.repositories.UserRepository;
import com.example.smartPos.repositories.model.RoleEntity;
import com.example.smartPos.repositories.model.UserEntity;
import com.example.smartPos.security.CustomUserDetailService;

import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

//    private final JwtService jwtService;

    private final CustomUserDetailService customUserDetailService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationManager authenticationManager,  CustomUserDetailService customUserDetailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    @PostMapping(path = "/user/register")
    public ResponseEntity<String> createUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new ResponseEntity<>("UserName is Taken", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        RoleEntity roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User Registered Success", HttpStatus.OK);
    }

    @PostMapping(path = "/user/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        if (authentication.isAuthenticated()){
//            String token = jwtService.generateToken(customUserDetailService.loadUserByUsername(loginRequest.getUsername()));
//            return new ResponseEntity<>(token, HttpStatus.OK);
//        }else{
//            throw new UserNameNotFoundException(ErrorCodes.USER_NAME_NOT_FOUND);
//        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("676776");
        loginResponse.setMessage("Login Success");
        return ResponseCreator.success(loginResponse);
    }



}
