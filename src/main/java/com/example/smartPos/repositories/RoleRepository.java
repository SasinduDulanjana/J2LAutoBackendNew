package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
 Optional<RoleEntity> findByName(String username);
}
