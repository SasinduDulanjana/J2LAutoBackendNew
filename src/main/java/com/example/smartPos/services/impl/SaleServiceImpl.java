package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.SaleRepository;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Sale;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.ProductConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<SaleResponse> getAllSales() {
        return saleRepository.findAll().stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sale.getSaleDate());
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setProducts(sale.getProducts());
            return saleResponse;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllSalesBySaleDate(String saleDate) {
        return saleRepository.findAllBySaleDate(saleDate).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sale.getSaleDate());
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setProducts(sale.getProducts());
            return saleResponse;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllSalesByCustId(Integer custId) {
        return saleRepository.findAllByCustId(custId).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sale.getSaleDate());
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setProducts(sale.getProducts());
            return saleResponse;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllSalesByUserId(Integer userId) {
        return saleRepository.findAllByUserId(userId).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sale.getSaleDate());
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setProducts(sale.getProducts());
            return saleResponse;
        }).toList();
    }

    @Override
    public SaleResponse getSaleBySaleId(Integer saleId) {
        Sale order = saleRepository.findById(saleId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.ORDER_NOT_FOUND)
        );
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(order.getSaleId());
        saleResponse.setCustId(order.getCustId());
        saleResponse.setUserId(order.getUserId());
        saleResponse.setSaleDate(order.getSaleDate());
        saleResponse.setTotalAmount(order.getTotalAmount());
        saleResponse.setInvoiceNumber(order.getInvoiceNumber());
        saleResponse.setProducts(order.getProducts());
        return saleResponse;
    }

    @Override
    public SaleResponse getSaleByInvoiceNumber(String invoiceNumber) {
        Sale order = saleRepository.findByInvoiceNumber(invoiceNumber).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.ORDER_NOT_FOUND)
        );
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(order.getSaleId());
        saleResponse.setCustId(order.getCustId());
        saleResponse.setUserId(order.getUserId());
        saleResponse.setSaleDate(order.getSaleDate());
        saleResponse.setTotalAmount(order.getTotalAmount());
        saleResponse.setInvoiceNumber(order.getInvoiceNumber());
        saleResponse.setProducts(order.getProducts());
        return saleResponse;
    }


    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest saleRequest) {

        Sale savedOrder = saleRepository.save(getSavedOrder(saleRequest));

        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(savedOrder.getSaleId());
        saleResponse.setCustId(savedOrder.getCustId());
        saleResponse.setUserId(savedOrder.getUserId());
        saleResponse.setSaleDate(savedOrder.getSaleDate());
        saleResponse.setTotalAmount(savedOrder.getTotalAmount());
        saleResponse.setInvoiceNumber(savedOrder.getInvoiceNumber());
        saleResponse.setProducts(savedOrder.getProducts());
        saleResponse.setStatusCode(ProductConstants.STATUS_201);
        saleResponse.setDesc(ProductConstants.MESSAGE_201);
        return saleResponse;
    }

    private Sale getSavedOrder(SaleRequest saleRequest) {
        Sale saveOrder = new Sale();
        saveOrder.setCustId(saleRequest.getCustId());
        saveOrder.setUserId(saleRequest.getUserId());
        saveOrder.setSaleDate(saleRequest.getOrderDate());
        saveOrder.setTotalAmount(saleRequest.getTotalAmount());
        saveOrder.setInvoiceNumber(saleRequest.getInvoiceNumber());
        saveOrder.fillNew("ADMIN USER");
        List<Product> products = saleRequest.getProducts().stream().map(product -> {
            if (product.getProductId() != null) {
                Product byProductIdAndSku = productRepository.findByProductIdAndSku(product.getProductId(), product.getSku());

                //Stock management logic
                Double currentTotalQty = byProductIdAndSku.getRemainingQty() - product.getRemainingQty();
                byProductIdAndSku.setRemainingQty(currentTotalQty);
                productRepository.save(byProductIdAndSku);

                return byProductIdAndSku;
            } else {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }
        }).toList();
        saveOrder.setProducts(products);
        return saveOrder;
    }

}
