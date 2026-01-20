package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Batch;
import com.example.smartPos.repositories.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    Optional<Batch> findByBatchNumberAndSku(String batchNumber, String sku);

    @Query("SELECT b FROM Batch b WHERE b.batchNumber IN :batchNumbers")
    List<Batch> findByBatchNumbers(@Param("batchNumbers") List<String> batchNumbers);

    List<Batch> findAllBySku(String sku);

    List<Batch> findAllBySkuIn(List<String> skus);

}
