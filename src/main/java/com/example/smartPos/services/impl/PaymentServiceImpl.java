package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.services.ICustomerService;
import com.example.smartPos.services.IPaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {
    @Override
    public List<CustomerResponse> getAllCustomers() {
        return null;
    }

    @Override
    public CustomerResponse getCustomerById(Integer customerId) {
        return null;
    }

    @Override
    public void createCustomer(CustomerRequest customerRequest) {

    }

    @Override
    public void updateCustomer(CustomerRequest customerRequest) {

    }
}
