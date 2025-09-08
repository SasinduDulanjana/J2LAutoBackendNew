package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.CommonResponse;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.repositories.model.Customer;
import com.example.smartPos.services.ICustomerService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

    ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/api/getAllCustomers")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/api/getCustomerByPhone/{mobileNo}")
    public ResponseEntity<CustomerResponse> getCustomerByPhone(@PathVariable String mobileNo) {
        CustomerResponse customerResponse = customerService.getCustomerByPhone(mobileNo);
        return ResponseCreator.success(customerResponse);
    }

    @GetMapping(path = "/api/getCustomerById/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseCreator.success(customerResponse);
    }

    @PostMapping(path = "/api/createCustomer")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse savedCustomer = customerService.createCustomer(customerRequest);
        return ResponseCreator.success(savedCustomer);
    }

    @PostMapping(path = "/api/updateCustomer")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse updatedCustomer = customerService.updateCustomer(customerRequest);
        return ResponseCreator.success(updatedCustomer);
    }

    @GetMapping("/api/getCustomersByPhone/{mobileNo}")
    public List<CustomerResponse> getCustomersByPhone(@PathVariable String mobileNo) {
        return customerService.getCustomersByPhoneNumber(mobileNo);
    }

    @PostMapping(path = "/api/deleteCustomer")
    public void deletedCustomer(@RequestBody CustomerRequest request) {
        customerService.deletCustomer(request.getCustId());
        System.out.println("OK");
    }

    @GetMapping("/getCustomersByName/{name}")
    public List<CustomerResponse> getCustomersByName(@PathVariable String name) {
        return customerService.getCustomersByName(name);
    }

    @GetMapping("/api/getCustomersByNameOrPhone/{nameOrPhone}")
    public ResponseEntity<List<CustomerResponse>> getCustomers(@PathVariable String nameOrPhone) {
        if (nameOrPhone != null) {
            List<CustomerResponse> customers = customerService.getCustomersByNameOrPhone(nameOrPhone);
            return ResponseEntity.ok(customers);
        } else {
            return ResponseEntity.ok(List.of());
        }
    }
}
