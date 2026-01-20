package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.RoleRequest;
import com.example.smartPos.controllers.responses.RoleResponse;
import com.example.smartPos.repositories.model.Role;

import java.util.List;

public interface IRoleService {

    List<RoleResponse> getAllRoles();

    RoleResponse getRole(Integer id);

    RoleResponse createRole(RoleRequest role);

    RoleResponse updateRole(Integer id, RoleRequest roleDetails);

    void deleteRole(Integer id);

    List<RoleResponse> getAllActiveRoles();

}
