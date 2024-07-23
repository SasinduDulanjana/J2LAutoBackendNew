package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CustomerResponse;

import java.util.List;

public interface IPaymentService {
    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Integer customerId);

    void createCustomer(CustomerRequest customerRequest);

    void updateCustomer(CustomerRequest customerRequest);
}
