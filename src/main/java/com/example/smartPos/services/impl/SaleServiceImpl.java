package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.mapper.SaleMapper;
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
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;

    private final SaleProductRepository saleProductRepository;

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    private final SaleMapper saleMapper;

    public SaleServiceImpl(SaleRepository saleRepository, SaleProductRepository saleProductRepository, ProductRepository productRepository, InventoryRepository inventoryRepository, SaleMapper saleMapper) {
        this.saleRepository = saleRepository;
        this.saleProductRepository = saleProductRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.saleMapper = saleMapper;
    }

    @Override
    public List<SaleResponse> getAllSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllByStatus(1).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setSoldProducts(sale.getSaleProducts());
            saleResponse.setSubTotal(sale.getSubTotal());
            saleResponse.setBillWiseDiscountPercentage(sale.getBillWiseDiscountPercentage());
            saleResponse.setBillWiseDiscountTotalAmount(sale.getBillWiseDiscountTotalAmount());
            saleResponse.setLineWiseDiscountTotalAmount(sale.getLineWiseDiscountTotalAmount());
            saleResponse.setFullyPaid(sale.getFullyPaid());
            saleResponse.setPaidAmount(sale.getPaidAmount());
            saleResponse.setHold(sale.getHold());
            saleResponse.setModifiedBy(sale.getModifiedBy());
            saleResponse.setModifiedDate(sm.format(sale.getModifiedDate()));
            return saleResponse;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllDeletedSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllByStatus(0).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setSoldProducts(sale.getSaleProducts());
            saleResponse.setSubTotal(sale.getSubTotal());
            saleResponse.setBillWiseDiscountPercentage(sale.getBillWiseDiscountPercentage());
            saleResponse.setBillWiseDiscountTotalAmount(sale.getBillWiseDiscountTotalAmount());
            saleResponse.setLineWiseDiscountTotalAmount(sale.getLineWiseDiscountTotalAmount());
            saleResponse.setFullyPaid(sale.getFullyPaid());
            saleResponse.setPaidAmount(sale.getPaidAmount());
            saleResponse.setHold(sale.getHold());
            saleResponse.setModifiedBy(sale.getModifiedBy());
            saleResponse.setModifiedDate(sm.format(sale.getModifiedDate()));
            return saleResponse;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllHoldSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllByIsHoldAndStatusNot(true, 0).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setSoldProducts(sale.getSaleProducts());
            saleResponse.setSubTotal(sale.getSubTotal());
            saleResponse.setBillWiseDiscountPercentage(sale.getBillWiseDiscountPercentage());
            saleResponse.setBillWiseDiscountTotalAmount(sale.getBillWiseDiscountTotalAmount());
            saleResponse.setLineWiseDiscountTotalAmount(sale.getLineWiseDiscountTotalAmount());
            saleResponse.setFullyPaid(sale.getFullyPaid());
            saleResponse.setPaidAmount(sale.getPaidAmount());
            saleResponse.setHold(sale.getHold());
            saleResponse.setModifiedBy(sale.getModifiedBy());
            saleResponse.setModifiedDate(sm.format(sale.getModifiedDate()));
            return saleResponse;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllPartiallyPaidSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllByIsFullyPaidAndStatusNot(false, 0).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setSoldProducts(sale.getSaleProducts());
            saleResponse.setSubTotal(sale.getSubTotal());
            saleResponse.setBillWiseDiscountPercentage(sale.getBillWiseDiscountPercentage());
            saleResponse.setBillWiseDiscountTotalAmount(sale.getBillWiseDiscountTotalAmount());
            saleResponse.setLineWiseDiscountTotalAmount(sale.getLineWiseDiscountTotalAmount());
            saleResponse.setFullyPaid(sale.getFullyPaid());
            saleResponse.setPaidAmount(sale.getPaidAmount());
            saleResponse.setHold(sale.getHold());
            saleResponse.setModifiedBy(sale.getModifiedBy());
            saleResponse.setModifiedDate(sm.format(sale.getModifiedDate()));
            return saleResponse;
        }).toList();
    }

    @Override
    public void deleteSale(Integer saleId) {
        try {
            Optional<Sale> optionalSale = saleRepository.findById(saleId);
            if (optionalSale.isPresent()) {
                Sale sale = optionalSale.get();
                sale.setStatus(0);
                sale.fillUpdated("ADMIN");
                saleRepository.save(sale);

                sale.getSaleProducts().stream().forEach(soldProduct -> {
                    if (soldProduct.getProduct().getProductId() != null) {
                        Product byProductIdAndSku = productRepository.findByProductIdAndSku(soldProduct.getProduct().getProductId(), soldProduct.getProduct().getSku());

                        if (byProductIdAndSku == null) {
                            throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
                        }
                        // Find and update inventory by SKU and batch number
                        Inventory inventory = inventoryRepository.findBySkuAndBatchNumber(byProductIdAndSku.getSku(), soldProduct.getProduct().getBatchNo())
                                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.INVENTORY_NOT_FOUND));

                        inventory.setQty(inventory.getQty() + soldProduct.getQuantity());
                        inventoryRepository.save(inventory);

                    } else {
                        throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
                    }
                });
            } else {
                throw new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new ServiceException("Error while deleting sale", e);
        }
    }

    @Override
    public void updatePaidAMount(SaleRequest request) {
        Optional<Sale> optionalSale = saleRepository.findById(request.getSaleId());
        if (optionalSale.isPresent()){
            Sale sale = optionalSale.get();
            Double paidAmount = request.getPaidAmount();
            Double existingAmount = sale.getPaidAmount();
            Double totalPaidAmount = existingAmount+paidAmount;
            sale.setPaidAmount(totalPaidAmount);
            if (sale.getTotalAmount() <= totalPaidAmount){
                sale.setFullyPaid(true);
            }
            saleRepository.save(sale);
        }

    }

    @Override
    public void deleteSalesByIds(List<Long> saleIds) {

    }

    @Override
    public List<SaleResponse> getAllSalesBySaleDate(String saleDate) {
//        return saleMapper.toSaleResponseList(saleRepository.findAllBySaleDate(saleDate));
        return null;
    }

    @Override
    public List<SaleResponse> getAllSalesByCustId(Integer custId) {
        return saleMapper.toSaleResponseList(saleRepository.findAllByCustId(custId));
    }

    @Override
    public List<SaleResponse> getAllSalesByUserId(Integer userId) {
        return saleMapper.toSaleResponseList(saleRepository.findAllByUserId(userId));
    }

    @Override
    public SaleResponse getSaleBySaleId(Integer saleId) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        Sale order = saleRepository.findById(saleId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.ORDER_NOT_FOUND)
        );
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(order.getSaleId());
        saleResponse.setCustId(order.getCustId());
        saleResponse.setUserId(order.getUserId());
        saleResponse.setSaleDate(sm.format(order.getSaleDate()));
        saleResponse.setTotalAmount(order.getTotalAmount());
        saleResponse.setInvoiceNumber(order.getInvoiceNumber());
        saleResponse.setSoldProducts(order.getSaleProducts());
        return saleResponse;
    }

    @Override
    public SaleResponse getSaleByInvoiceNumber(String invoiceNumber) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        Sale order = saleRepository.findByInvoiceNumber(invoiceNumber).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.ORDER_NOT_FOUND)
        );
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(order.getSaleId());
        saleResponse.setCustId(order.getCustId());
        saleResponse.setUserId(order.getUserId());
        saleResponse.setSaleDate(sm.format(order.getSaleDate()));
        saleResponse.setTotalAmount(order.getTotalAmount());
        saleResponse.setInvoiceNumber(order.getInvoiceNumber());
        saleResponse.setSoldProducts(order.getSaleProducts());
        return saleResponse;
    }


    @Override
    @Transactional
    public SaleResponse createSale(SaleRequest saleRequest) {
        try {
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
            saveSale.setPaidAmount(saleRequest.getPaidAmount());
            saveSale.setHold(saleRequest.isHold());
            saveSale.setFullyPaid(saleRequest.isFullyPaid());
            saveSale.setStatus(1);
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

                    if (inventory.getQty() > 0){
                        inventory.setQty(inventory.getQty() - soldProduct.getProduct().getRemainingQty());
                        inventoryRepository.save(inventory);
                    }else {
                        throw new ServiceException("Quantity is not enough");
                    }


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

            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(savedOrder.getSaleId());
            saleResponse.setCustId(savedOrder.getCustId());
            saleResponse.setUserId(savedOrder.getUserId());
            saleResponse.setSaleDate(sm.format(savedOrder.getSaleDate()));
            saleResponse.setTotalAmount(savedOrder.getTotalAmount());
            saleResponse.setInvoiceNumber(savedOrder.getInvoiceNumber());
            saleResponse.setSoldProducts(savedOrder.getSaleProducts());
            saleResponse.setStatusCode(SaleConstants.STATUS_201);
            saleResponse.setDesc(SaleConstants.MESSAGE_201);
            return saleResponse;
        } catch (Exception e) {
            throw new ServiceException("Saving Unsuccessful", e);
        }
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

    @Override
    public List<SaleResponse> getSalesByDateRange(Date startDate, Date endDate) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllBySaleDateBetweenAndStatusNot(startDate, endDate).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustId(sale.getCustId());
            saleResponse.setUserId(sale.getUserId());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            saleResponse.setSoldProducts(sale.getSaleProducts());
            saleResponse.setSubTotal(sale.getSubTotal());
            saleResponse.setBillWiseDiscountPercentage(sale.getBillWiseDiscountPercentage());
            saleResponse.setBillWiseDiscountTotalAmount(sale.getBillWiseDiscountTotalAmount());
            saleResponse.setLineWiseDiscountTotalAmount(sale.getLineWiseDiscountTotalAmount());
            saleResponse.setFullyPaid(sale.getFullyPaid());
            saleResponse.setPaidAmount(sale.getPaidAmount());
            saleResponse.setHold(sale.getHold());
            saleResponse.setModifiedBy(sale.getModifiedBy());
            saleResponse.setModifiedDate(sm.format(sale.getModifiedDate()));
            return saleResponse;
        }).toList();
    }

}
