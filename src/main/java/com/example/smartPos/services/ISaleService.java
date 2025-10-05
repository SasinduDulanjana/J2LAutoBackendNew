package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.PaymentDetailsRequest;
import com.example.smartPos.controllers.requests.ProductStatusUpdateRequest;
import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.requests.SalesReturnRequest;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.repositories.model.PaymentDetails;

import java.util.Date;
import java.util.List;

public interface ISaleService {
    List<SaleResponse> getAllSales();

    List<SaleResponse> getAllDeletedSales();

    List<SaleResponse> getAllHoldSales();

//    List<SaleResponse> getAllPartiallyPaidSales();

//    void deleteSale(Integer saleId);

    void deleteHoldSale(Integer saleId);

    void updatePaidAMount(SaleRequest request);

    SaleResponse createSale(SaleRequest saleRequest);

    String generateInvoiceNumber();

    List<SaleResponse> getSalesByDateRange(Date startDate, Date endDate);

    List<SoldProductResponse> getProductsForSale(Integer saleId);

    SaleResponse getSaleByInvoiceNumber(String invoiceNumber);

    void processSalesReturn(SalesReturnRequest salesReturnRequest);

    List<SalesReturnResponse> getAllSalesReturns();

    List<PaymentDetails> getPaymentDetailsByInvoice(String invoiceNumber);

    PaymentDetailsResponse createPaymentDetails(PaymentDetailsRequest paymentDetailsRequest);

    void sendSaleDetailsSms(Integer saleId);

    List<CustomerViewSaleResponse> fetchSaleProductsOfCustomerView(Integer saleId);

    List<ProductStatusUpdateResponse> updateProductStatus(ProductStatusUpdateRequest request);
}
