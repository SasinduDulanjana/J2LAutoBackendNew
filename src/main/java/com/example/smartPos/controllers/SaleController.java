package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.*;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.services.ISaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sale")
@CrossOrigin
public class SaleController {

    private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }


    @GetMapping(path = "/api/getAllSale")
    public List<SaleResponse> getAllSale() {
        return saleService.getAllSales();
    }

    @GetMapping(path = "/api/getAllDeletedSales")
    public List<SaleResponse> getAllDeletedSales() {
        return saleService.getAllDeletedSales();
    }

    @GetMapping(path = "/api/getAllHoldSales")
    public List<SaleResponse> getAllHoldSales() {
        return saleService.getAllHoldSales();
    }

//    @GetMapping(path = "/api/getAllPartiallyPaidSales")
//    public List<SaleResponse> getAllPartiallyPaidSales() {
//        return saleService.getAllPartiallyPaidSales();
//    }

    @PostMapping(path = "/api/deleteHoldSale")
    public void deleteHoldSale(@RequestBody SaleRequest request) {
        saleService.deleteHoldSale(request.getSaleId());
        System.out.println("OK");
    }

    @PostMapping(path = "/api/updatePaidAMount")
    public void updatePaidAMount(@RequestBody SaleRequest request) {
        saleService.updatePaidAMount(request);
        System.out.println("OK");
    }

    @PostMapping(path = "/api/createSale")
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest saleRequest) {
        SaleResponse saleResponse = saleService.createSale(saleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleResponse);
    }

    @GetMapping(path = "/api/getInvoiceNumber")
    public String getInvoiceNumber() {
        return saleService.generateInvoiceNumber();
    }


    @GetMapping(path = "/api/getSalesByDateRange")
    public List<SaleResponse> getSalesByDateRange(@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                  @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        return saleService.getSalesByDateRange(startDate, endDate);
    }

    @PostMapping(path = "/api/getProductsForSale/{saleId}")
    public ResponseEntity<List<SoldProductResponse>> getProductsForSale(@PathVariable Integer saleId) {
        List<SoldProductResponse> soldProductResponses = saleService.getProductsForSale(saleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(soldProductResponses);
    }

    @GetMapping(path = "/api/getSaleByInvoiceNumber/{invoiceNumber}")
    public ResponseEntity<SaleResponse> getSaleByInvoiceNumber(@PathVariable String invoiceNumber) {
        SaleResponse saleResponse = saleService.getSaleByInvoiceNumber(invoiceNumber);
        return ResponseEntity.ok(saleResponse);
    }

    @PostMapping(path = "/api/salesReturn")
    public ResponseEntity<String> processSalesReturn(@RequestBody SalesReturnRequest salesReturnRequest) {
        saleService.processSalesReturn(salesReturnRequest);
        return ResponseEntity.ok("Sales return processed successfully.");
    }

    @GetMapping(path = "/api/getAllSalesReturn")
    public ResponseEntity<List<SalesReturnResponse>> getAllSalesReturn() {
        List<SalesReturnResponse> salesReturnResponseList = saleService.getAllSalesReturns();
        return ResponseEntity.ok(salesReturnResponseList);
    }

    @GetMapping(path = "/api/getPaymentDetailsByInvoice/{invoiceNumber}")
    public ResponseEntity<List<PaymentDetails>> getPaymentDetailsByInvoice(@PathVariable String invoiceNumber) {
        List<PaymentDetails> paymentDetails = saleService.getPaymentDetailsByInvoice(invoiceNumber);
        return ResponseEntity.ok(paymentDetails);
    }

    @PostMapping(path = "/api/createPaymentDetails")
    public ResponseEntity<PaymentDetailsResponse> createPaymentDetails(@RequestBody PaymentDetailsRequest paymentDetailsRequest) {
        PaymentDetailsResponse paymentDetailsResponse = saleService.createPaymentDetails(paymentDetailsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentDetailsResponse);
    }

    @GetMapping("/api/saleDetails")
    public ResponseEntity<List<SoldProductResponse>> getSaleDetails(@RequestParam String saleId) {
        List<SoldProductResponse> soldProducts = saleService.getProductsForSale(Integer.parseInt(saleId));
        return ResponseEntity.ok(soldProducts);
    }

    @PostMapping("/api/sendSaleDetailsSms/{saleId}")
    public ResponseEntity<String> sendSaleDetailsSms(@PathVariable Integer saleId) {
        try {
            saleService.sendSaleDetailsSms(saleId);
            return ResponseEntity.ok("SMS sent successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @GetMapping("/api/fetchSaleProductsOfCustomerView/{saleId}")
    public ResponseEntity<List<CustomerViewSaleResponse>> fetchSaleProductsOfCustomerView(@PathVariable Integer saleId) {
        List<CustomerViewSaleResponse> customerViewSaleResponseList = saleService.fetchSaleProductsOfCustomerView(saleId);
        return ResponseEntity.ok(customerViewSaleResponseList);
    }

    @PostMapping(path = "/api/updateProductStatus")
    public ResponseEntity<List<ProductStatusUpdateResponse>> updateProductStatus(@RequestBody ProductStatusUpdateRequest request) {
        List<ProductStatusUpdateResponse> response = saleService.updateProductStatus(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/fetchPaymentByInvoiceNumber/{invoiceNumber}")
    public ResponseEntity<PaymentResponse> fetchPaymentByInvoiceNumber(@PathVariable String invoiceNumber) {
        PaymentResponse response = saleService.getPaymentByInvoice(invoiceNumber);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/updateDiscount")
    public ResponseEntity<String> updateDiscount(@RequestBody EditDiscountAmountRequest request) {
        saleService.editDisocuntAmount(request);
        return ResponseEntity.ok("Discount amount updated successfully.");
    }
}
