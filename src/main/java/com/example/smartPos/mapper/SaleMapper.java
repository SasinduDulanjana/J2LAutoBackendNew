package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.repositories.model.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleResponse toSaleResponse(Sale sale);

    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "modifiedBy", target = "modifiedBy")
    @Mapping(source = "saleProducts", target = "soldProducts")
    @Mapping(source = "saleDate", target = "saleDate")
    List<SaleResponse> toSaleResponseList(List<Sale> sales);
}
