package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.*;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.services.IProductService;
import com.example.smartPos.services.IPurchaseService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/purchase")
public class PurchaseController {

    IPurchaseService purchaseService;

    public PurchaseController(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @GetMapping(path = "/api/getAllPurchase")
    public List<PurchaseResponse> getALlPurchase() {
        return purchaseService.getAllPurchases();
    }

    @PostMapping(path = "/api/createPurchase")
    public ResponseEntity<PurchaseResponse> createPurchase(@RequestBody PurchaseRequest purchaseRequest) {
        PurchaseResponse savedPurchase = purchaseService.createPurchase(purchaseRequest);
        return ResponseCreator.success(savedPurchase);
    }


    @GetMapping(path = "/api/getPurchaseById/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Integer id) {
        PurchaseResponse purchaseResponse = purchaseService.getPurchaseById(id);
        return ResponseCreator.success(purchaseResponse);
    }

    @GetMapping(path = "/api/getPurchasesByDateRange")
    public List<PurchaseResponse> getPurchasesByDateRange(@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                          @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        return purchaseService.getPurchasesByDateRange(startDate, endDate);
    }

    @PostMapping(path = "/api/getProductBatchDetails")
    public ResponseEntity<ProductBatchResponse> getProductBatchDetails(@RequestBody ProductBatchRequest request) {
        ProductBatchResponse response = purchaseService.fetchProductBatchDetails(request);
        return ResponseCreator.success(response);
    }

    @GetMapping(path = "/api/getPurchaseByIdentifier/{identifier}")
    public ResponseEntity<PurchaseResponse> getPurchaseByIdentifier(@PathVariable String identifier) {
        PurchaseResponse purchaseResponse = purchaseService.getPurchaseByIdentifier(identifier);
        return ResponseCreator.success(purchaseResponse);
    }

    @PostMapping(path = "/api/purchaseReturn")
    public ResponseEntity<PurchaseReturnResponse> processSalesReturn(@RequestBody PurchaseReturnRequest purchaseReturnRequest) {
        PurchaseReturnResponse purchaseReturnResponse = purchaseService.processPurchaseReturn(purchaseReturnRequest);
        return ResponseCreator.success(purchaseReturnResponse);
    }

    @GetMapping(path = "/api/getAllPurchaseReturn")
    public ResponseEntity<List<PurchaseReturnResponse>> getAllPurchaseReturn() {
        List<PurchaseReturnResponse> responses = purchaseService.getAllPurchaseReturns();
        return ResponseEntity.ok(responses);
    }

}
