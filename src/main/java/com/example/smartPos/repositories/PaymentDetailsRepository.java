package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Payment;
import com.example.smartPos.repositories.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
    List<PaymentDetails> findByPayment(Payment payment);

    @Query("SELECT pd FROM PaymentDetails pd " +
            "JOIN FETCH pd.payment p " +
            "WHERE p.referenceId IN :invoiceNumbers " +
            "AND p.paymentType = 'RECEIPT' " +
            "AND p.referenceType = 'SALE'")
    List<PaymentDetails> findByInvoiceNumbersAndReceiptPaymentTypeAndSalePaymentType(@Param("invoiceNumbers") List<String> invoiceNumbers);

    @Query("SELECT pd FROM PaymentDetails pd JOIN FETCH pd.payment p WHERE p.paymentType = 'RECEIPT' AND p.referenceType = 'SALE'")
    List<PaymentDetails> findAllByReceiptPaymentTypeAndSalePaymentType();

    @Query("SELECT pd FROM PaymentDetails pd JOIN FETCH pd.payment p WHERE p.paymentType = 'PAYMENT' AND p.referenceType = 'PURCHASE'")
    List<PaymentDetails> findAllByPaymentPaymentTypeAndPurchaseReferenceType();

    @Query("SELECT pd FROM PaymentDetails pd " +
            "JOIN FETCH pd.payment p " +
            "WHERE p.referenceId IN :purchaseIds " +
            "AND p.paymentType = 'PAYMENT' " +
            "AND p.referenceType = 'PURCHASE'")
    List<PaymentDetails> findByInvoiceNumbersAndPaymentPaymentTypeAndPurchasePaymentType(@Param("purchaseIds") List<String> purchaseIds);

    @Query("SELECT pd FROM PaymentDetails pd " +
            "JOIN FETCH pd.payment p " +
            "WHERE p.referenceId = :purchaseId " +
            "AND p.referenceType = 'PURCHASE'")
    List<PaymentDetails> findByPurchaseId(@Param("purchaseId") String purchaseId);

    @Query("SELECT pd FROM PaymentDetails pd JOIN FETCH pd.payment p WHERE pd.paymentMethod = 'CHEQUE'")
    List<PaymentDetails> findAllChequeDetails();

    @Query("SELECT pd FROM PaymentDetails pd " +
            "JOIN FETCH pd.payment p " +
            "WHERE pd.chequeNo = :chequeNo")
    Optional<PaymentDetails> findByChequeNo(@Param("chequeNo") String chequeNo);
}
