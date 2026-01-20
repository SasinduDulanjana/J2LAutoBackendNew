package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.PaymentDetailsResponse;
import com.example.smartPos.controllers.responses.VehicleResponse;
import com.example.smartPos.repositories.model.PaymentDetails;
import com.example.smartPos.repositories.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentDetailsMapper {
    PaymentDetailsMapper INSTANCE = Mappers.getMapper(PaymentDetailsMapper.class);

    PaymentDetailsResponse toPaymentDetailsResponse(PaymentDetails paymentDetails);
}
