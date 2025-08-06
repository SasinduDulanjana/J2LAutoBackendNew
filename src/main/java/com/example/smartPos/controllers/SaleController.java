package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
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

    @GetMapping(path = "/api/getAllPartiallyPaidSales")
    public List<SaleResponse> getAllPartiallyPaidSales() {
        return saleService.getAllPartiallyPaidSales();
    }

    @PostMapping(path = "/api/deleteSale")
    public void deletedSales(@RequestBody SaleRequest request) {
        saleService.deleteSale(request.getSaleId());
        System.out.println("OK");
    }

    @PostMapping(path = "/api/updatePaidAMount")
    public void updatePaidAMount(@RequestBody SaleRequest request) {
        saleService.updatePaidAMount(request);
        System.out.println("OK");
    }

    @PostMapping(path = "/api/deleteSales")
    public void deleteSales(@RequestBody List<Long> saleIds) {

    }

//    @GetMapping(path = "/api/getCustomerById")
//    public CustomerResponse getCustomerById(Integer customerId) {
//        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
//        return customerResponse;
//    }

    @PostMapping(path = "/api/createSale")
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest saleRequest) {
        SaleResponse saleResponse = saleService.createSale(saleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleResponse);
    }

    @GetMapping(path = "/api/getInvoiceNumber")
    public String getInvoiceNumber() {
        return saleService.generateInvoiceNumber();
    }

//    @PostMapping(path = "/api/updateCustomer")
//    public void updateCustomer(@RequestBody CustomerRequest customerRequest) {
//        customerService.updateCustomer(customerRequest);
//    }

}
