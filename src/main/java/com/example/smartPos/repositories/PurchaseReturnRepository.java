package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.PurchaseReturn;
import com.example.smartPos.repositories.model.SalesReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, Integer> {
    List<PurchaseReturn> findByPurchase_PurchaseId(Integer purchaseId);
}
