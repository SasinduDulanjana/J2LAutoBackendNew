package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.RoleRequest;
import com.example.smartPos.controllers.responses.RoleResponse;
import com.example.smartPos.repositories.model.Role;
import com.example.smartPos.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public List<RoleResponse> getAllRoles() { return roleService.getAllRoles(); }

    @GetMapping("/{id}")
    public RoleResponse getRole(@PathVariable Integer id) { return roleService.getRole(id); }

    @PostMapping
    public RoleResponse createRole(@RequestBody RoleRequest role) { return roleService.createRole(role); }

    @PutMapping("/{id}")
    public RoleResponse updateRole(@PathVariable Integer id, @RequestBody RoleRequest role) { return roleService.updateRole(id, role); }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Integer id) { roleService.deleteRole(id); }

    @GetMapping("/active")
    public List<RoleResponse> getAllActiveRoles() {
        return roleService.getAllActiveRoles();
    }

}
