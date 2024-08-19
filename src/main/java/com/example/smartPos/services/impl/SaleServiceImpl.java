package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.InventoryRepository;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.SaleRepository;
import com.example.smartPos.repositories.model.Inventory;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Sale;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.SaleConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
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
        saleResponse.setStatusCode(SaleConstants.STATUS_201);
        saleResponse.setDesc(SaleConstants.MESSAGE_201);
        return saleResponse;
    }

    private Sale getSavedOrder(SaleRequest saleRequest) {
        Sale saveSale = new Sale();
        saveSale.setCustId(saleRequest.getCustId());
        saveSale.setUserId(saleRequest.getUserId());
        saveSale.setInvoiceNumber(saleRequest.getInvoiceNumber());
        saveSale.setSaleDate(saleRequest.getOrderDate());
        saveSale.setTotalAmount(saleRequest.getTotalAmount());
        saveSale.fillNew("ADMIN USER");
        List<Product> products = saleRequest.getSoldProducts().stream().map(product -> {
            if (product.getProductId() != null) {
                Product byProductIdAndSku = productRepository.findByProductIdAndSku(product.getProductId(), product.getSku());

                // Update inventory
                Optional<Inventory> inventoryBySkuAndBatchNumber = inventoryRepository.findBySkuAndBatchNumber(byProductIdAndSku.getSku(), product.getBatchNo());
                //if inventory already exist update the qty
                if (inventoryBySkuAndBatchNumber.isPresent()) {
                    inventoryBySkuAndBatchNumber.get().setQty(inventoryBySkuAndBatchNumber.get().getQty() - product.getRemainingQty());
                } else {
                    throw new ResourceNotFoundException(ErrorCodes.INVENTORY_NOT_FOUND);
                }
                return byProductIdAndSku;
            } else {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }
        }).toList();
        saveSale.setProducts(products);
        return saveSale;
    }

}
