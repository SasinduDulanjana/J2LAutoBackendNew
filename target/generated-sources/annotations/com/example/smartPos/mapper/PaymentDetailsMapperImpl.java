package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.PaymentDetailsResponse;
import com.example.smartPos.repositories.model.PaymentDetails;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-06T10:21:42+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PaymentDetailsMapperImpl implements PaymentDetailsMapper {

    @Override
    public PaymentDetailsResponse toPaymentDetailsResponse(PaymentDetails paymentDetails) {
        if ( paymentDetails == null ) {
            return null;
        }

        PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();

        paymentDetailsResponse.setAmount( paymentDetails.getAmount() );
        paymentDetailsResponse.setChequeNo( paymentDetails.getChequeNo() );
        paymentDetailsResponse.setBankName( paymentDetails.getBankName() );
        paymentDetailsResponse.setChequeDate( paymentDetails.getChequeDate() );
        paymentDetailsResponse.setPaymentStatus( paymentDetails.getPaymentStatus() );

        return paymentDetailsResponse;
    }
}
