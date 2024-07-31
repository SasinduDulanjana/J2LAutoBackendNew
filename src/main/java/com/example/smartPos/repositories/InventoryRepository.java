package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.repositories.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findBySkuAndBatchNumber(String sku, String batchNumber);

}
