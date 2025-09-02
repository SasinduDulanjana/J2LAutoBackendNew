package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.SaleResponse;

import java.util.Date;
import java.util.List;

public interface ISaleService {
    List<SaleResponse> getAllSales();

    List<SaleResponse> getAllDeletedSales();

    List<SaleResponse> getAllHoldSales();

    List<SaleResponse> getAllPartiallyPaidSales();

    void deleteSale(Integer saleId);

    void deleteHoldSale(Integer saleId);

    void updatePaidAMount(SaleRequest request);

    void deleteSalesByIds(List<Long> saleIds);

    List<SaleResponse> getAllSalesBySaleDate(String orderDate);

    List<SaleResponse> getAllSalesByCustId(Integer custId);

    List<SaleResponse> getAllSalesByUserId(Integer userId);

    SaleResponse getSaleBySaleId(Integer orderId);

    SaleResponse getSaleByInvoiceNumber(String invoiceNumber);

    SaleResponse createSale(SaleRequest saleRequest);

    String generateInvoiceNumber();

    List<SaleResponse> getSalesByDateRange(Date startDate, Date endDate);

}
