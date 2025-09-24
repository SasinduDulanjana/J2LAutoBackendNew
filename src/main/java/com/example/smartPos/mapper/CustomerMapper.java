package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.repositories.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    // Map CustomerRequest to Customer
    Customer toCustomer(CustomerRequest customerRequest);

    // Map Customer to CustomerRequest
    CustomerRequest toCustomerRequest(Customer customer);
}
