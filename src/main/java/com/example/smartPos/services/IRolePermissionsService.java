package com.example.smartPos.services;

import com.example.smartPos.repositories.model.RolePermission;

import java.util.List;

public interface IRolePermissionsService {

    List<RolePermission> getAllPermissions();

    List<RolePermission> getPermissionsByRole(String role);

    RolePermission savePermission(RolePermission permission);

    List<RolePermission> saveAllPermissions(List<RolePermission> permissions);
}
