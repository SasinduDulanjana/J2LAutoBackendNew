package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Payment;
import com.example.smartPos.repositories.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
    List<PaymentDetails> findByPayment(Payment payment);

    @Query("SELECT pd FROM PaymentDetails pd WHERE pd.payment.referenceId IN :invoiceNumbers AND pd.payment.paymentType = 'RECEIPT' AND pd.payment.referenceType = 'SALE'")
    List<PaymentDetails> findByInvoiceNumbersAndReceiptPaymentTypeAndSalePaymentType(@Param("invoiceNumbers") List<String> invoiceNumbers);

    @Query("SELECT pd FROM PaymentDetails pd JOIN FETCH pd.payment p WHERE p.paymentType = 'RECEIPT' AND p.referenceType = 'SALE'")
    List<PaymentDetails> findAllByReceiptPaymentTypeAndSalePaymentType();

    @Query("SELECT pd FROM PaymentDetails pd JOIN pd.payment p WHERE p.customer.custId = :custId AND p.paymentType = 'RECEIPT' AND p.referenceType = 'SALE'")
    List<PaymentDetails> findPaymentDetailsByCustomerId(@Param("custId") Integer custId);
}
