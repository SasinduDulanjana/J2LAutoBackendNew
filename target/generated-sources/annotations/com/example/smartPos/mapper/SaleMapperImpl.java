package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.repositories.model.Sale;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-13T20:16:04+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class SaleMapperImpl implements SaleMapper {

    @Override
    public SaleResponse toSaleResponse(Sale sale) {
        if ( sale == null ) {
            return null;
        }

        SaleResponse saleResponse = new SaleResponse();

        saleResponse.setFullyPaid( sale.getFullyPaid() );
        saleResponse.setHold( sale.getHold() );
        saleResponse.setAddBy( sale.getAddBy() );
        saleResponse.setModifiedBy( sale.getModifiedBy() );
        saleResponse.setAddedDate( sale.getAddedDate() );
        if ( sale.getModifiedDate() != null ) {
            saleResponse.setModifiedDate( new SimpleDateFormat().format( sale.getModifiedDate() ) );
        }
        saleResponse.setSaleId( sale.getSaleId() );
        saleResponse.setCustId( sale.getCustId() );
        saleResponse.setUserId( sale.getUserId() );
        if ( sale.getSaleDate() != null ) {
            saleResponse.setSaleDate( new SimpleDateFormat().format( sale.getSaleDate() ) );
        }
        saleResponse.setTotalAmount( sale.getTotalAmount() );
        saleResponse.setInvoiceNumber( sale.getInvoiceNumber() );
        saleResponse.setSubTotal( sale.getSubTotal() );
        saleResponse.setBillWiseDiscountPercentage( sale.getBillWiseDiscountPercentage() );
        saleResponse.setBillWiseDiscountTotalAmount( sale.getBillWiseDiscountTotalAmount() );
        saleResponse.setLineWiseDiscountTotalAmount( sale.getLineWiseDiscountTotalAmount() );
        saleResponse.setStatus( sale.getStatus() );
        saleResponse.setPaidAmount( sale.getPaidAmount() );

        return saleResponse;
    }

    @Override
    public List<SaleResponse> toSaleResponseList(List<Sale> sales) {
        if ( sales == null ) {
            return null;
        }

        List<SaleResponse> list = new ArrayList<SaleResponse>( sales.size() );
        for ( Sale sale : sales ) {
            list.add( toSaleResponse( sale ) );
        }

        return list;
    }
}
