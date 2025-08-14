package com.example.smartPos.services;

import com.example.smartPos.controllers.responses.FeatureResponse;
import com.example.smartPos.repositories.model.RolePermission;

import java.util.List;

public interface IFeatureService {

    List<FeatureResponse> getAllFeatures();

}

