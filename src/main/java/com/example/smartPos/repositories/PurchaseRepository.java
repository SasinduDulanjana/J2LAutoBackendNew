package com.example.smartPos.repositories;

import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.repositories.model.Purchase;
import com.example.smartPos.repositories.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findAllByPurchaseName(String name);

    Optional<Purchase> findByInvoiceNumber(String invoiceNumber);

    List<Purchase> findAllBySupplierId(Integer supplierId);

    List<Purchase> findAllByInvoiceDate(String invoiceDate);

    List<Purchase> findAllByPaymentStatus(String paymentStatus);

    @Query("SELECT p FROM Purchase p LEFT JOIN FETCH p.products WHERE p.purchaseId = :purchaseId")
    Optional<Purchase> findByIdWithProducts(@Param("purchaseId") Integer purchaseId);

    @Query("SELECT p FROM Purchase p WHERE p.addedDate BETWEEN :startDate AND :endDate AND p.status != 0")
    List<Purchase> findAllByAddedDateBetweenAndStatusNot(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COALESCE(SUM(p.totalCost), 0) FROM Purchase p WHERE p.status = 1")
    Double findTotalTotalCostForActivePurchases();

    @Query("SELECT COALESCE(SUM(p.totalCost), 0) - COALESCE(SUM(p.paidAmount), 0) FROM Purchase p WHERE p.status = 1")
    Double findDueAmountForActivePurchases();

    @Query("SELECT DATE_FORMAT(p.addedDate, '%Y-%m') AS month, COALESCE(SUM(p.totalCost), 0) AS purchases FROM Purchase p WHERE p.status = 1 GROUP BY DATE_FORMAT(p.addedDate, '%Y-%m') ORDER BY month")
    List<Object[]> findMonthlyPurchases();

    @Query("SELECT p FROM Purchase p WHERE p.supplierId IN (SELECT s.supId FROM Supplier s)")
    List<Purchase> findAllWithSupplier();

    @Query("SELECT p FROM Purchase p WHERE p.supplierId = :supId")
    List<Purchase> findAllPurchasesWithSupplier(@Param("supId") Integer supId);
}
