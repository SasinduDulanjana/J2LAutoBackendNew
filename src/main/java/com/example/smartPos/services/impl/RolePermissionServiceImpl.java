package com.example.smartPos.services.impl;

import com.example.smartPos.repositories.RolePermissionRepository;
import com.example.smartPos.repositories.model.RolePermission;
import com.example.smartPos.services.IRolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
//    @Override
//    public List<RolePermission> saveAllPermissions(List<RolePermission> updatedPermissions) {
//        for (RolePermission permission : updatedPermissions) {
//            // Check if a record with the same role and feature exists
//            RolePermission existingPermission = repository.findByRoleAndFeature(permission.getRole(), permission.getFeature());
//            if (existingPermission != null) {
//                // Update the enabled status
//                existingPermission.setEnabled(permission.isEnabled());
//                repository.save(existingPermission);
//            } else {
//                // Add a new record
//                repository.save(permission);
//            }
//        }
//        return repository.findAll();
//    }

    @Override
    public List<RolePermission> saveAllPermissions(List<RolePermission> updatedPermissions) {
        // Fetch all existing permissions for the roles in the updatedPermissions list
        List<String> roles = updatedPermissions.stream()
                .map(RolePermission::getRole)
                .distinct()
                .toList();
        List<RolePermission> existingPermissions = repository.findByRoleIn(roles);

        // Create a map for quick lookup of existing permissions by role and feature
        Map<String, RolePermission> permissionMap = existingPermissions.stream()
                .collect(Collectors.toMap(
                        p -> p.getRole() + ":" + p.getFeature(),
                        p -> p
                ));

        // Iterate through the updated permissions and update or add them
        for (RolePermission permission : updatedPermissions) {
            String key = permission.getRole() + ":" + permission.getFeature();
            if (permissionMap.containsKey(key)) {
                // Update the existing permission
                RolePermission existingPermission = permissionMap.get(key);
                existingPermission.setEnabled(permission.isEnabled());
            } else {
                // Add the new permission
                existingPermissions.add(permission);
            }
        }

        // Save all changes in a single batch operation
        repository.saveAll(existingPermissions);

        return repository.findAll();
    }
}
