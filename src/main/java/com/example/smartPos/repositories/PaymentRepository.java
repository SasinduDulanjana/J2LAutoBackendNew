package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.referenceId = :referenceId AND p.paymentType = 'RECEIPT' AND p.referenceType = 'SALE'")
    Optional<Payment> findByReferenceIdAndReceiptPaymentTypeAndSaleReferenceType(@Param("referenceId") String referenceId);
}
