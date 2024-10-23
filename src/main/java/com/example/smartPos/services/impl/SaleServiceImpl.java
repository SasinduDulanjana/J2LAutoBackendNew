package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.InventoryRepository;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.SaleProductRepository;
import com.example.smartPos.repositories.SaleRepository;
import com.example.smartPos.repositories.model.Inventory;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Sale;
import com.example.smartPos.repositories.model.SaleProduct;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.SaleConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;

    private final SaleProductRepository saleProductRepository;

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    public SaleServiceImpl(SaleRepository saleRepository, SaleProductRepository saleProductRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.saleRepository = saleRepository;
        this.saleProductRepository = saleProductRepository;
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
            saleResponse.setProducts(sale.getSaleProducts());
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
            saleResponse.setProducts(sale.getSaleProducts());
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
            saleResponse.setProducts(sale.getSaleProducts());
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
            saleResponse.setProducts(sale.getSaleProducts());
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
        saleResponse.setProducts(order.getSaleProducts());
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
        saleResponse.setProducts(order.getSaleProducts());
        return saleResponse;
    }


    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest saleRequest) {

        Sale saveSale = new Sale();
        saveSale.setCustId(saleRequest.getCustId());
        saveSale.setUserId(saleRequest.getUserId());
        saveSale.setInvoiceNumber(saleRequest.getInvoiceNumber());
        saveSale.setSaleDate(saleRequest.getOrderDate());
        saveSale.setSubTotal(saleRequest.getSubTotal());
        saveSale.setTotalAmount(saleRequest.getTotalAmount());
        saveSale.setLineWiseDiscountTotalAmount(saleRequest.getLineWiseDiscountTotalAmount());
        saveSale.setBillWiseDiscountPercentage(saleRequest.getBillWiseDiscountPercentage());
        saveSale.setBillWiseDiscountTotalAmount(saleRequest.getBillWiseDiscountTotalAmount());
        saveSale.fillNew("ADMIN USER");

        List<SaleProduct> saleProductList = saleRequest.getSoldProducts().stream().map(soldProduct -> {
            if (soldProduct.getProduct().getProductId() != null) {
                Product byProductIdAndSku = productRepository.findByProductIdAndSku(soldProduct.getProduct().getProductId(), soldProduct.getProduct().getSku());

                if (byProductIdAndSku == null) {
                    throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
                }
                // Find and update inventory by SKU and batch number
                Inventory inventory = inventoryRepository.findBySkuAndBatchNumber(byProductIdAndSku.getSku(), soldProduct.getProduct().getBatchNo())
                        .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.INVENTORY_NOT_FOUND));

                inventory.setQty(inventory.getQty() - soldProduct.getProduct().getRemainingQty());
                inventoryRepository.save(inventory);

                SaleProduct saleProduct = new SaleProduct();
                saleProduct.setSale(saveSale);
                saleProduct.setProduct(byProductIdAndSku);
                saleProduct.setQuantity(soldProduct.getQuantity());
                saleProduct.setDiscountAmount(soldProduct.getDiscountAmount());
                saleProduct.setDiscountPercentage(soldProduct.getDiscountPercentage());
                saleProduct.setDiscountedTotal(soldProduct.getDiscountedTotal());
                return saleProduct;
            } else {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }
        }).toList();

        // Set SaleProducts in the Sale object
        saveSale.setSaleProducts(saleProductList);
        // Save the Sale along with SaleProducts in one go
        Sale savedOrder = saleRepository.save(saveSale);


        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(savedOrder.getSaleId());
        saleResponse.setCustId(savedOrder.getCustId());
        saleResponse.setUserId(savedOrder.getUserId());
        saleResponse.setSaleDate(savedOrder.getSaleDate());
        saleResponse.setTotalAmount(savedOrder.getTotalAmount());
        saleResponse.setInvoiceNumber(savedOrder.getInvoiceNumber());
        saleResponse.setProducts(savedOrder.getSaleProducts());
        saleResponse.setStatusCode(SaleConstants.STATUS_201);
        saleResponse.setDesc(SaleConstants.MESSAGE_201);
        return saleResponse;
    }

    @Override
    public String generateInvoiceNumber() {
        Sale lastSale = saleRepository.findTopByOrderBySaleIdDesc();
        if (lastSale.getInvoiceNumber() != null) {
            int invoiceNum = Integer.parseInt(lastSale.getInvoiceNumber().replace("INV_", ""));
            return "INV_" + (invoiceNum + 1);
        } else {
            return "INV_1001";
        }
    }

}
