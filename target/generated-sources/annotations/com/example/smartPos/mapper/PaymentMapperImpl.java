package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.PaymentResponse;
import com.example.smartPos.repositories.model.Payment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-12T23:18:47+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponse toPaymentDetailsResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponse paymentResponse = new PaymentResponse();

        paymentResponse.setPaymentId( payment.getPaymentId() );
        paymentResponse.setPaymentType( payment.getPaymentType() );
        paymentResponse.setReferenceId( payment.getReferenceId() );
        paymentResponse.setReferenceType( payment.getReferenceType() );
        paymentResponse.setCustomer( payment.getCustomer() );
        paymentResponse.setSupplier( payment.getSupplier() );
        paymentResponse.setPaymentDate( payment.getPaymentDate() );
        paymentResponse.setTotalAmount( payment.getTotalAmount() );
        paymentResponse.setRemarks( payment.getRemarks() );

        return paymentResponse;
    }
}
