package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.PurchaseReturn;
import com.example.smartPos.repositories.model.SalesReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseReturnRepository extends JpaRepository<PurchaseReturn, Integer> {
    List<PurchaseReturn> findByPurchase_PurchaseId(Integer purchaseId);

    @Query("SELECT pr FROM PurchaseReturn pr JOIN Supplier s ON pr.supplierId = s.supId")
    List<PurchaseReturn> findAllWithSupplier();

    List<PurchaseReturn> findBySupplierId(Integer supplierId);

    @Query("SELECT FUNCTION('DATE_FORMAT', p.returnDate, '%Y-%m') AS month, SUM(CAST(p.refundAmount AS double)) " +
            "FROM PurchaseReturn p " +
            "GROUP BY FUNCTION('DATE_FORMAT', p.returnDate, '%Y-%m')")
    List<Object[]> findMonthlyPurchaseReturns();

    @Query("SELECT SUM(CAST(p.refundAmount AS double)) FROM PurchaseReturn p")
    Double findTotalPurchaseReturns();
}
