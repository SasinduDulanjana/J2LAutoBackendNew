package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.repositories.model.Purchase;

import java.util.List;
import java.util.Optional;

public interface IPurchaseService {
    List<PurchaseResponse> getAllPurchases();
    List<PurchaseResponse> getAllByPurchaseName(String purchaseName);

    List<PurchaseResponse> getAllBySupplierId(Integer supplierId);

    List<PurchaseResponse> getAllByInvoiceDate(String date);

    List<PurchaseResponse> getAllByPaymentStatus(String status);
    PurchaseResponse getPurchaseByInvoiceNumber(String invoiceNumber);

    PurchaseResponse createPurchase(PurchaseRequest purchaseRequest);

//    PurchaseResponse updatePurchase(PurchaseRequest purchaseRequest);
}
