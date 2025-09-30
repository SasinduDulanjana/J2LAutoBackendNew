package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.repositories.model.Customer;
import com.example.smartPos.repositories.model.Purchase;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {
    PurchaseMapper INSTANCE = Mappers.getMapper(PurchaseMapper.class);
    Purchase toPurchase(PurchaseRequest purchaseRequest);
    PurchaseResponse toPurchaseResponse(Purchase purchase);
}
