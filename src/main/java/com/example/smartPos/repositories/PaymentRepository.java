package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p " +
            "JOIN FETCH p.customer c " +
            "WHERE p.referenceId = :referenceId " +
            "AND p.paymentType = 'RECEIPT' " +
            "AND p.referenceType = 'SALE'")
    Optional<Payment> findByReferenceIdAndReceiptPaymentTypeAndSaleReferenceType(@Param("referenceId") String referenceId);

    @Query("SELECT p FROM Payment p " +
            "JOIN FETCH p.supplier s " +
            "WHERE p.referenceId = :referenceId " +
            "AND p.paymentType = 'PAYMENT' " +
            "AND p.referenceType = 'PURCHASE'")
    Optional<Payment> findByReferenceIdAndPaymentPaymentTypeAndPurchaseReferenceType(@Param("referenceId") String referenceId);
}
