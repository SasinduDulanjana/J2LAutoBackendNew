package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.CommonResponse;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.services.IProductService;
import com.example.smartPos.services.IPurchaseService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
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
//
//    @GetMapping(path = "/api/getProductsByName/{name}")
//    public List<ProductResponse> getProductsByName(@PathVariable String name) {
//        return productService.getProductsByName(name);
//    }
//
//    @GetMapping(path = "/api/getProductByBarcode/{barcode}")
//    public ResponseEntity<CommonResponse> getProductsByBarcode(@PathVariable String barcode) {
//        ProductResponse productResponse = productService.getProductByBarcode(barcode);
//        return ResponseCreator.success(productResponse);
//    }
//
//    @GetMapping(path = "/api/getProductBySku/{sku}")
//    public ResponseEntity<CommonResponse> getProductBySku(@PathVariable String sku) {
//        ProductResponse productResponse = productService.getProductBySku(sku);
//        return ResponseCreator.success(productResponse);
//    }
//
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
//
//    @PostMapping(path = "/api/updateProduct")
//    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest) {
//        ProductResponse updatedProduct = productService.updateProduct(productRequest);
//        return ResponseCreator.success(updatedProduct);
//    }

}
