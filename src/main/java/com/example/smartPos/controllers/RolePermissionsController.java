package com.example.smartPos.controllers;

import com.example.smartPos.repositories.model.RolePermission;
import com.example.smartPos.services.IRolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-permissions")
@CrossOrigin
public class RolePermissionsController {

    @Autowired
    private IRolePermissionsService service;

    @GetMapping
    public List<RolePermission> getAllPermissions() {
        return service.getAllPermissions();
    }

    @PostMapping
    public List<RolePermission> savePermissions(@RequestBody List<RolePermission> updatedPermissions) {
        return service.saveAllPermissions(updatedPermissions);
    }
}
