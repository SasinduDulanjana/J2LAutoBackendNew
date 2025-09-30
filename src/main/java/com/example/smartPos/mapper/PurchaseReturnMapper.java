package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.ReturnItems;
import com.example.smartPos.controllers.responses.PurchaseReturnResponse;
import com.example.smartPos.controllers.responses.SalesReturnResponse;
import com.example.smartPos.repositories.model.PurchaseReturn;
import com.example.smartPos.repositories.model.SalesReturn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseReturnMapper {
    PurchaseReturnMapper INSTANCE = Mappers.getMapper(PurchaseReturnMapper.class);

    @Mapping(target = "purchaseId", source = "purchase.purchaseId")
    @Mapping(target = "supplierName", source = "supplierName")
    @Mapping(target = "invoiceNumber", source = "invoiceNumber")
    @Mapping(target = "returns", expression = "java(mapReturns(purchaseReturn))")
    PurchaseReturnResponse toPurchaseReturnResponse(PurchaseReturn purchaseReturn);

    default List<ReturnItems> mapReturns(PurchaseReturn purchaseReturn) {
        ReturnItems returnItem = new ReturnItems();
        returnItem.setProductName(purchaseReturn.getProduct().getProductName());
        returnItem.setQuantityToReturn(purchaseReturn.getQuantityReturned());
        returnItem.setCondition(purchaseReturn.getCondition());
        returnItem.setReason(purchaseReturn.getReason());
        returnItem.setRefundAmount(purchaseReturn.getRefundAmount());
        returnItem.setBatchNumber(purchaseReturn.getBatchNumber());
        return List.of(returnItem);
    }
}
