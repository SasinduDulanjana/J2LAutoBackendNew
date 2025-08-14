package com.example.smartPos.services.impl;

import com.example.smartPos.repositories.RolePermissionRepository;
import com.example.smartPos.repositories.model.RolePermission;
import com.example.smartPos.services.IRolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements IRolePermissionsService {

    @Autowired
    private RolePermissionRepository repository;

    @Override
    public List<RolePermission> getAllPermissions() {
        return repository.findAll();
    }
    @Override
    public List<RolePermission> getPermissionsByRole(String role) {
        return repository.findByRole(role);
    }
    @Override
    public RolePermission savePermission(RolePermission permission) {
        return repository.save(permission);
    }
    @Override
    public List<RolePermission> saveAllPermissions(List<RolePermission> updatedPermissions) {
        for (RolePermission permission : updatedPermissions) {
            // Check if a record with the same role and feature exists
            RolePermission existingPermission = repository.findByRoleAndFeature(permission.getRole(), permission.getFeature());
            if (existingPermission != null) {
                // Update the enabled status
                existingPermission.setEnabled(permission.isEnabled());
                repository.save(existingPermission);
            } else {
                // Add a new record
                repository.save(permission);
            }
        }
        return repository.findAll();
    }
}
