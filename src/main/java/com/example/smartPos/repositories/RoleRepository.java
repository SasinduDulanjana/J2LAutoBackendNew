package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
 Optional<Role> findByName(String username);
}
