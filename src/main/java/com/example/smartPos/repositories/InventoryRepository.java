package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.repositories.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findBySkuAndBatchNumber(String sku, String batchNumber);

    @Query("SELECT i.qty FROM Inventory i WHERE i.sku = :sku AND i.batchNumber = :batchNumber")
    Double findAvailableQuantityBySkuAndBatch(@Param("sku") String sku, @Param("batchNumber") String batchNumber);

    @Query("SELECT i FROM Inventory i WHERE i.sku = :sku")
    List<Inventory> findAllBySku(@Param("sku") String sku);
}
