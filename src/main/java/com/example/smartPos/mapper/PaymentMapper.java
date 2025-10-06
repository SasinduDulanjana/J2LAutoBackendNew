package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.PaymentResponse;
import com.example.smartPos.repositories.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentResponse toPaymentDetailsResponse(Payment payment);
}
