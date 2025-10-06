package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.PaymentDetailsRequest;
import com.example.smartPos.controllers.requests.ProductBatchRequest;
import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.requests.PurchaseReturnRequest;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.repositories.model.Purchase;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPurchaseService {
    List<PurchaseResponse> getAllPurchases();

    // Repository method using a custom query
    List<PurchaseResponse> getAllByPurchaseName(String purchaseName);

    List<PurchaseResponse> getAllBySupplierId(Integer supplierId);

    List<PurchaseResponse> getAllByInvoiceDate(String date);

    List<PurchaseResponse> getAllByPaymentStatus(String status);
    PurchaseResponse getPurchaseByInvoiceNumber(String invoiceNumber);

    PurchaseResponse createPurchase(PurchaseRequest purchaseRequest);

    PurchaseResponse getPurchaseById(Integer purchaseId);

    List<PurchaseResponse> getPurchasesByDateRange(Date startDate, Date endDate);

    ProductBatchResponse fetchProductBatchDetails(ProductBatchRequest request);

    PurchaseResponse getPurchaseByIdentifier(String identifier);

    PurchaseReturnResponse processPurchaseReturn(PurchaseReturnRequest purchaseReturnRequest);

    List<PurchaseReturnResponse> getAllPurchaseReturns();

    List<PaymentDetails> getPaymentDetailsByPurchaseId(Integer purchaseId);

    PaymentDetailsResponse createPaymentDetails(PaymentDetailsRequest paymentDetailsRequest);

    PaymentResponse fetchPaymentByPurchaseId(String invoiceNumber);
}
