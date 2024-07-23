package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    List<Purchase> findAllByPurchaseName(String name);

    Optional<Purchase> findByInvoiceNumber(String invoiceNumber);

    List<Purchase> findAllBySupplierId(Integer supplierId);

    List<Purchase> findAllByInvoiceDate(String invoiceDate);

    List<Purchase> findAllByPaymentStatus(String paymentStatus);
}
