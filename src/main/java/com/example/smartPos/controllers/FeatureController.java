package com.example.smartPos.controllers;

import com.example.smartPos.controllers.responses.FeatureResponse;
import com.example.smartPos.repositories.model.RolePermission;
import com.example.smartPos.services.IFeatureService;
import com.example.smartPos.services.IRolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private IFeatureService service;

    @GetMapping
    public List<FeatureResponse> getAllPermissions() {
        return service.getAllFeatures();
    }

}
