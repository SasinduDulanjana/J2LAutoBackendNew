package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.ReturnItems;
import com.example.smartPos.controllers.responses.SalesReturnResponse;
import com.example.smartPos.controllers.responses.VehicleResponse;
import com.example.smartPos.repositories.model.SalesReturn;
import com.example.smartPos.repositories.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalesReturnMapper {
    SalesReturnMapper INSTANCE = Mappers.getMapper(SalesReturnMapper.class);

    @Mapping(target = "saleId", source = "sale.saleId")
    @Mapping(target = "customerName", source = "customerName")
    @Mapping(target = "invoiceNumber", source = "invoiceNumber")
    @Mapping(target = "returns", expression = "java(mapReturns(salesReturn))")
    SalesReturnResponse toSalesReturnResponse(SalesReturn salesReturn);

    default List<ReturnItems> mapReturns(SalesReturn salesReturn) {
        ReturnItems returnItem = new ReturnItems();
        returnItem.setProductName(salesReturn.getProduct().getProductName());
        returnItem.setQuantityToReturn(salesReturn.getQuantityReturned());
        returnItem.setCondition(salesReturn.getCondition());
        returnItem.setReason(salesReturn.getReason());
        returnItem.setRefundAmount(salesReturn.getRefundAmount());
        returnItem.setBatchNumber(salesReturn.getBatchNumber());
        return List.of(returnItem);
    }
}
