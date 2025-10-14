package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.PurchaseReturnResponse;
import com.example.smartPos.repositories.model.Purchase;
import com.example.smartPos.repositories.model.PurchaseReturn;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-12T23:18:47+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PurchaseReturnMapperImpl implements PurchaseReturnMapper {

    @Override
    public PurchaseReturnResponse toPurchaseReturnResponse(PurchaseReturn purchaseReturn) {
        if ( purchaseReturn == null ) {
            return null;
        }

        PurchaseReturnResponse purchaseReturnResponse = new PurchaseReturnResponse();

        purchaseReturnResponse.setPurchaseId( purchaseReturnPurchasePurchaseId( purchaseReturn ) );
        purchaseReturnResponse.setSupplierName( purchaseReturn.getSupplierName() );
        purchaseReturnResponse.setInvoiceNumber( purchaseReturn.getInvoiceNumber() );
        if ( purchaseReturn.getReturnDate() != null ) {
            purchaseReturnResponse.setReturnDate( new SimpleDateFormat().format( purchaseReturn.getReturnDate() ) );
        }

        purchaseReturnResponse.setReturns( mapReturns(purchaseReturn) );

        return purchaseReturnResponse;
    }

    private Integer purchaseReturnPurchasePurchaseId(PurchaseReturn purchaseReturn) {
        if ( purchaseReturn == null ) {
            return null;
        }
        Purchase purchase = purchaseReturn.getPurchase();
        if ( purchase == null ) {
            return null;
        }
        Integer purchaseId = purchase.getPurchaseId();
        if ( purchaseId == null ) {
            return null;
        }
        return purchaseId;
    }
}
