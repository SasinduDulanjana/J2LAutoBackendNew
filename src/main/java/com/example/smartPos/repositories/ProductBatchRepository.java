package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.ProductBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBatchRepository extends JpaRepository<ProductBatch, Integer> {

    ProductBatch findByPurchaseIdAndProduct_ProductId(Integer purchaseId, Integer productId);
}
