package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.repositories.model.Customer;

import java.util.List;

public interface ICustomerService {
    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerByPhone(String mobileNo);

    CustomerResponse getCustomerById(Integer custId);

    CustomerResponse createCustomer(CustomerRequest customerRequest);

    CustomerResponse updateCustomer(CustomerRequest customerRequest);

    List<CustomerResponse> getCustomersByPhoneNumber(String phone);

    void deletCustomer(Integer custId);
}
