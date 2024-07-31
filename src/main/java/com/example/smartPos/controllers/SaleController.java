package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.services.ICustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {

    ICustomerService customerService;
    public SaleController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/api/getAllSale")
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> customerResponse = customerService.getAllCustomers();
        return customerResponse;
    }

//    @GetMapping(path = "/api/getCustomerById")
//    public CustomerResponse getCustomerById(Integer customerId) {
//        CustomerResponse customerResponse = customerService.getCustomerById(customerId);
//        return customerResponse;
//    }

    @PostMapping(path = "/api/createSale")
    public void createCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.createCustomer(customerRequest);
    }

    @PostMapping(path = "/api/updateCustomer")
    public void updateCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomer(customerRequest);
    }

}
