package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
 Optional<User> findByUsername(String username);
 Boolean existsByUsername(String username);

 Boolean existsByEmail(String email);
 Boolean existsByUsernameAndPassword(String username, String password);
}
