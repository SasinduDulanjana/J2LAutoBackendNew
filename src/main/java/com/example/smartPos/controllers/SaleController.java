package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }


//    @GetMapping(path = "/api/getAllSale")
//    public List<CustomerResponse> getAllCustomers() {
//        List<CustomerResponse> customerResponse = customerService.getAllCustomers();
//        return customerResponse;
//    }

//    @GetMapping(path = "/api/getCustomerById")
//    public CustomerResponse getCustomerById(Integer customerId) {
//        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
//        return customerResponse;
//    }

    @PostMapping(path = "/api/createSale")
    public ResponseEntity<SaleResponse> createSale(@RequestBody SaleRequest saleRequest) {
        SaleResponse saleResponse = saleService.createSale(saleRequest);
        return ResponseCreator.success(saleResponse);
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
