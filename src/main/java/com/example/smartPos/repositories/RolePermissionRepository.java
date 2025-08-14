package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.RolePermission;
import com.example.smartPos.repositories.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    List<RolePermission> findByRole(String role);

    RolePermission findByRoleAndFeature(String role, String feature);
}
