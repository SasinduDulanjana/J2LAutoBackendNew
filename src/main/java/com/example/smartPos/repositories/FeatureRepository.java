package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Feature;
import com.example.smartPos.repositories.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {

}
