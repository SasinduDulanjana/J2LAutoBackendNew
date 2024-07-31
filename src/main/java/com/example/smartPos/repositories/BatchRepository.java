package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Batch;
import com.example.smartPos.repositories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    Optional<Batch> findByBatchNumberAndSku(String batchNumber, String sku);

    List<Batch> findAllBySku(String sku);

}
