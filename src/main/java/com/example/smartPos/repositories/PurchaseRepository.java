package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Purchase;
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
}
