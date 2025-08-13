package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.RoleRequest;
import com.example.smartPos.controllers.responses.RoleResponse;
import com.example.smartPos.repositories.RoleRepository;
import com.example.smartPos.repositories.model.Role;
import com.example.smartPos.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> {
                    RoleResponse roleResponse = new RoleResponse();
                    roleResponse.setId(role.getId());
                    roleResponse.setName(role.getName());
                    roleResponse.setDescription(role.getDescription());
                    roleResponse.setStatus(role.getStatus());
                    return roleResponse;
                })
                .collect(Collectors.toList());
    }

    public RoleResponse getRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        roleResponse.setStatus(role.getStatus());
        return roleResponse;
    }

    public RoleResponse createRole(RoleRequest roleRequest) {
        // Map RoleRequest to Role entity
        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setDescription(roleRequest.getDescription());
        role.setStatus(roleRequest.getStatus());

        // Save the Role entity
        Role savedRole = roleRepository.save(role);

        // Map the saved Role entity to RoleResponse
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(savedRole.getId());
        roleResponse.setName(savedRole.getName());
        roleResponse.setDescription(savedRole.getDescription());
        roleResponse.setStatus(savedRole.getStatus());

        return roleResponse;
    }

    public RoleResponse updateRole(Integer id, RoleRequest roleDetails) {
        // Fetch the Role entity by ID
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Update the Role entity with new details
        role.setName(roleDetails.getName());
        role.setDescription(roleDetails.getDescription());
        role.setStatus(roleDetails.getStatus());

        // Save the updated Role entity
        Role updatedRole = roleRepository.save(role);

        // Map the updated Role entity to RoleResponse
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(updatedRole.getId());
        roleResponse.setName(updatedRole.getName());
        roleResponse.setDescription(updatedRole.getDescription());
        roleResponse.setStatus(updatedRole.getStatus());

        return roleResponse;
    }

    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        role.setStatus("0"); // Mark the role as deleted
        roleRepository.save(role); // Save the updated role
    }

    public List<RoleResponse> getAllActiveRoles() {
        return roleRepository.findAll().stream()
                .filter(role -> "1".equals(role.getStatus())) // Filter active roles
                .map(role -> {
                    RoleResponse roleResponse = new RoleResponse();
                    roleResponse.setId(role.getId());
                    roleResponse.setName(role.getName());
                    roleResponse.setDescription(role.getDescription());
                    roleResponse.setStatus(role.getStatus());
                    return roleResponse;
                })
                .collect(Collectors.toList());
    }

}
