package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.SalesReturnResponse;
import com.example.smartPos.repositories.model.Sale;
import com.example.smartPos.repositories.model.SalesReturn;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-04T08:37:20+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class SalesReturnMapperImpl implements SalesReturnMapper {

    @Override
    public SalesReturnResponse toSalesReturnResponse(SalesReturn salesReturn) {
        if ( salesReturn == null ) {
            return null;
        }

        SalesReturnResponse salesReturnResponse = new SalesReturnResponse();

        salesReturnResponse.setSaleId( salesReturnSaleSaleId( salesReturn ) );
        salesReturnResponse.setCustomerName( salesReturn.getCustomerName() );
        salesReturnResponse.setInvoiceNumber( salesReturn.getInvoiceNumber() );
        if ( salesReturn.getReturnDate() != null ) {
            salesReturnResponse.setReturnDate( new SimpleDateFormat().format( salesReturn.getReturnDate() ) );
        }

        salesReturnResponse.setReturns( mapReturns(salesReturn) );

        return salesReturnResponse;
    }

    private Integer salesReturnSaleSaleId(SalesReturn salesReturn) {
        if ( salesReturn == null ) {
            return null;
        }
        Sale sale = salesReturn.getSale();
        if ( sale == null ) {
            return null;
        }
        Integer saleId = sale.getSaleId();
        if ( saleId == null ) {
            return null;
        }
        return saleId;
    }
}
