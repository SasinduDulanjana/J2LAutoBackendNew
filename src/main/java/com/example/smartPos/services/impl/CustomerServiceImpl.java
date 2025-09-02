package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.CustomerRepository;
import com.example.smartPos.repositories.model.Customer;
import com.example.smartPos.services.ICustomerService;
import com.example.smartPos.util.CustomerConstants;
import com.example.smartPos.util.ErrorCodes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<CustomerResponse> getAllCustomers() {

        List<Customer> customerList = customerRepository.findAllByStatus(1);
        return customerList.stream().map(customer -> {
            CustomerResponse custResp = new CustomerResponse();
            custResp.setCustId(customer.getCustId());
            custResp.setPhone(customer.getPhone());
            custResp.setName(customer.getName());
            custResp.setAddress(customer.getAddress());
            custResp.setEmail(customer.getEmail());
            custResp.setStatus(customer.getStatus());
            return custResp;
        }).toList();

    }

    @Override
    public CustomerResponse getCustomerByPhone(String mobileNo) {
        Customer customer = customerRepository.findByPhone(mobileNo).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
        );
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustId(customer.getCustId());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setName(customer.getName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomerById(Integer custId) {
        Customer customer = customerRepository.findById(custId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
        );
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustId(customer.getCustId());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setName(customer.getName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findByPhone(customerRequest.getPhone());
        if (customer.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_CUSTOMER);
        }

        // Retrieve the currently authenticated user's username
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer saveCustomer = new Customer();
        saveCustomer.setName(customerRequest.getName());
        saveCustomer.setAddress(customerRequest.getAddress());
        saveCustomer.setPhone(customerRequest.getPhone());
        saveCustomer.setEmail(customerRequest.getEmail());
        saveCustomer.setStatus(1);
        saveCustomer.fillNew(currentUser);
        Customer savedCustomer = customerRepository.save(saveCustomer);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setPhone(savedCustomer.getPhone());
        customerResponse.setStatusCode(CustomerConstants.STATUS_201);
        customerResponse.setDesc(CustomerConstants.MESSAGE_201);
        return customerResponse;
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
        CustomerResponse updatedCustomerResponse = new CustomerResponse();
        if (customerRequest.getCustId() != null) {
            Customer customer = customerRepository.findById(customerRequest.getCustId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
            );
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            customer.setPhone(customerRequest.getPhone());
            customer.setName(customerRequest.getName());
            customer.setAddress(customerRequest.getAddress());
            customer.setEmail(customerRequest.getEmail());
            customer.fillUpdated(currentUser);
            Customer updatedCustomer = customerRepository.save(customer);

            updatedCustomerResponse.setCustId(updatedCustomer.getCustId());
            updatedCustomerResponse.setPhone(updatedCustomer.getPhone());
            updatedCustomerResponse.setName(updatedCustomer.getName());
            updatedCustomerResponse.setAddress(updatedCustomer.getAddress());
            updatedCustomerResponse.setEmail(updatedCustomer.getEmail());
        }
        return updatedCustomerResponse;
    }

    public List<CustomerResponse> getCustomersByPhoneNumber(String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhoneContaining(phoneNumber);
        List<CustomerResponse> customerDtos = customers.stream()
                .map(customer -> {
                    CustomerResponse customerResponse = new CustomerResponse();
                    customerResponse.setCustId(customer.getCustId());
                    customerResponse.setPhone(customer.getPhone());
                    customerResponse.setName(customer.getName());
                    return customerResponse;
                }).toList();

        return customerDtos;
    }

    @Override
    public void deletCustomer(Integer custId) {
        Optional<Customer> customerOptional = customerRepository.findById(custId);
        if (customerOptional.isPresent()){
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerOptional.get();
            customer.setStatus(0);
            customer.fillUpdated(currentUser);
            customerRepository.save(customer);
        }
    }

    @Override
    public List<CustomerResponse> getCustomersByName(String name) {
        List<Customer> customers = customerRepository.findByNameContaining(name);
        return customers.stream().map(customer -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setCustId(customer.getCustId());
            customerResponse.setName(customer.getName());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setAddress(customer.getAddress());
            customerResponse.setEmail(customer.getEmail());
            return customerResponse;
        }).toList();
    }

    @Override
    public List<CustomerResponse> getCustomersByNameOrPhone(String nameOrPhone) {
        List<Customer> customers;
        if (nameOrPhone != null) {
            customers = customerRepository.findByNameOrPhone(nameOrPhone);
        } else {
            customers = List.of();
        }

        return customers.stream().map(customer -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setCustId(customer.getCustId());
            customerResponse.setName(customer.getName());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setAddress(customer.getAddress());
            customerResponse.setEmail(customer.getEmail());
            return customerResponse;
        }).toList();
    }
}
