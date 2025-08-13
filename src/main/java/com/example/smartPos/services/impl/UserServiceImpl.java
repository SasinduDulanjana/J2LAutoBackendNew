package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.UserRequest;
import com.example.smartPos.controllers.responses.UserResponse;
import com.example.smartPos.repositories.RoleRepository;
import com.example.smartPos.repositories.UserRepository;
import com.example.smartPos.repositories.model.Role;
import com.example.smartPos.repositories.model.User;
import com.example.smartPos.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());
            userResponse.setStatus(user.getStatus());
            userResponse.setRoles(user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toList()));
            return userResponse;
        }).collect(Collectors.toList());
    }

    public UserResponse getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setStatus(user.getStatus());
        userResponse.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));
        return userResponse;
    }

    public UserResponse createUser(UserRequest userRequest) {
        // Check for duplicate users by username
        boolean userExists = userRepository.existsByUsername(userRequest.getUsername());
        if (userExists) {
            throw new RuntimeException("A user with the same username already exists");
        }

        // Check for duplicate users by email
        boolean emailExists = userRepository.existsByEmail(userRequest.getEmail());
        if (emailExists) {
            throw new RuntimeException("A user with the same email address already exists");
        }

        // Create a new User entity
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setStatus(userRequest.getStatus());

        // Fetch roles from the database if role names are provided
        if (userRequest.getRoles() != null) {
            List<Role> roles = userRequest.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName).orElse(null)) // Assuming findByName exists
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }
        // Save the User entity
        User savedUser = userRepository.save(user);

        // Map the saved User entity to a UserResponse
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setUsername(savedUser.getUsername());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setStatus(savedUser.getStatus());
        userResponse.setRoles(savedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        return userResponse;
    }

    public UserResponse updateUser(Integer id, UserRequest userDetails) {
        // Fetch the user by ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate duplicate username
        if (!user.getUsername().equals(userDetails.getUsername()) &&
                userRepository.existsByUsername(userDetails.getUsername())) {
            throw new RuntimeException("A user with the same username already exists");
        }

        // Validate duplicate email
        if (!user.getEmail().equals(userDetails.getEmail()) &&
                userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("A user with the same email address already exists");
        }

        // Update user details
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword())); // Encrypt password
        user.setEmail(userDetails.getEmail());
        user.setStatus(userDetails.getStatus());

        // Update roles if provided
        if (userDetails.getRoles() != null) {
            List<Role> roles = userDetails.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName).orElse(null)) // Assuming findByName exists
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            user.setRoles(roles);
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        // Map the updated user to UserResponse
        UserResponse userResponse = new UserResponse();
        userResponse.setId(updatedUser.getId());
        userResponse.setUsername(updatedUser.getUsername());
        userResponse.setEmail(updatedUser.getEmail());
        userResponse.setStatus(updatedUser.getStatus());
        userResponse.setRoles(updatedUser.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

        return userResponse;
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus("0"); // Set the status to indicate soft deletion
        userRepository.save(user); // Save the updated user
    }

}
