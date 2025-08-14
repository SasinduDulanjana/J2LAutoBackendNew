package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.responses.FeatureResponse;
import com.example.smartPos.repositories.FeatureRepository;
import com.example.smartPos.repositories.RolePermissionRepository;
import com.example.smartPos.repositories.model.Feature;
import com.example.smartPos.repositories.model.RolePermission;
import com.example.smartPos.services.IFeatureService;
import com.example.smartPos.services.IRolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureServiceImpl implements IFeatureService {

    @Autowired
    private FeatureRepository repository;

    @Override
    public List<FeatureResponse> getAllFeatures() {
        return repository.findAll().stream().map(feature -> {
            FeatureResponse response = new FeatureResponse();
            response.setId(feature.getId());
            response.setFeatureName(feature.getFeatureName());
            response.setDescription(feature.getDescription());
            return response;
        }).toList();
    }

}
