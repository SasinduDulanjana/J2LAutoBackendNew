package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.repositories.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

}
