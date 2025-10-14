package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.repositories.model.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-12T23:18:47+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toCustomer(CustomerRequest customerRequest) {
        if ( customerRequest == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setCustId( customerRequest.getCustId() );
        customer.setName( customerRequest.getName() );
        customer.setEmail( customerRequest.getEmail() );
        customer.setPhone( customerRequest.getPhone() );
        customer.setAddress( customerRequest.getAddress() );

        return customer;
    }

    @Override
    public CustomerRequest toCustomerRequest(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerRequest customerRequest = new CustomerRequest();

        customerRequest.setName( customer.getName() );
        customerRequest.setEmail( customer.getEmail() );
        customerRequest.setPhone( customer.getPhone() );
        customerRequest.setAddress( customer.getAddress() );
        customerRequest.setCustId( customer.getCustId() );

        return customerRequest;
    }
}
