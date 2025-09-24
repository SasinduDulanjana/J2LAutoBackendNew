package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SaleRequest;
import com.example.smartPos.controllers.requests.SoldProductRequest;
import com.example.smartPos.controllers.responses.SaleResponse;
import com.example.smartPos.controllers.responses.SoldProductResponse;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.mapper.CustomerMapper;
import com.example.smartPos.mapper.UserMapper;
import com.example.smartPos.repositories.BatchRepository;
import com.example.smartPos.repositories.InventoryRepository;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.SaleRepository;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.SaleConstants;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements ISaleService {

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    private final BatchRepository batchRepository;

    private final CustomerMapper customerMapper;

    private final UserMapper userMapper;

    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository, InventoryRepository inventoryRepository, BatchRepository batchRepository, CustomerMapper customerMapper, UserMapper userMapper) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.batchRepository = batchRepository;
        this.customerMapper = customerMapper;
        this.userMapper = userMapper;
    }

    @Override
    public List<SaleResponse> getAllSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllByStatus(1).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustomer(sale.getCustomer());
            saleResponse.setUser(sale.getUser());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
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
    public List<SoldProductResponse> getProductsForSale(Integer saleId) {
        Sale sale = saleRepository.findSaleWithProductsById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND));

        // Collect all batch numbers from the sold products
        List<String> batchNumbers = sale.getSaleProducts().stream()
                .map(SaleProduct::getBatchNo)
                .distinct()
                .toList();

        // Fetch all batches in a single query and map batchNo to retailPrice
        Map<String, Double> batchRetailPrices = batchRepository.findByBatchNumbers(batchNumbers).stream()
                .collect(Collectors.toMap(Batch::getBatchNumber, Batch::getRetailPrice));

        // Map SaleProducts to SoldProductResponse
        return sale.getSaleProducts().stream().map(soldProduct -> {
            SoldProductResponse response = new SoldProductResponse();
            response.setSaleProductId(soldProduct.getSaleProductId());
            response.setProduct(soldProduct.getProduct());
            response.setSale(soldProduct.getSale());
            response.setBatchNo(soldProduct.getBatchNo());
            response.setRetailPrice(batchRetailPrices.getOrDefault(soldProduct.getBatchNo(), null));
            response.setQuantity(soldProduct.getQuantity());
            response.setDiscountPercentage(soldProduct.getDiscountPercentage());
            response.setDiscountAmount(soldProduct.getDiscountAmount());
            response.setDiscountedTotal(soldProduct.getDiscountedTotal());
            return response;
        }).toList();
    }

    @Override
    public List<SaleResponse> getAllDeletedSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return saleRepository.findAllByStatus(0).stream().map(sale -> {
            SaleResponse saleResponse = new SaleResponse();
            saleResponse.setSaleId(sale.getSaleId());
            saleResponse.setCustomer(sale.getCustomer());
            saleResponse.setUser(sale.getUser());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
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
            saleResponse.setCustomer(sale.getCustomer());
            saleResponse.setUser(sale.getUser());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
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
    public void deleteHoldSale(Integer saleId) {
        try {
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Sale> optionalSale = saleRepository.findById(saleId);
            if (optionalSale.isPresent()) {
                Sale sale = optionalSale.get();
                sale.setStatus(0);
                sale.fillUpdated(currentUser);
                saleRepository.save(sale);
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
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            Double paidAmount = request.getPaidAmount();
            Double existingAmount = sale.getPaidAmount();
            Double totalPaidAmount = existingAmount + paidAmount;
            sale.setPaidAmount(totalPaidAmount);
            if (sale.getTotalAmount() <= totalPaidAmount) {
                sale.setFullyPaid(true);
            }
            saleRepository.save(sale);
        }

    }


//    @Override
//    @Transactional
//    public SaleResponse createSale(SaleRequest saleRequest) {
//        try {
//            // Retrieve the currently authenticated user's username
//            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//
//            Sale saveSale = new Sale();
//            saveSale.setCustomer(customerMapper.toCustomer(saleRequest.getCustomer()));
//            saveSale.setUser(userMapper.toUser(saleRequest.getUser()));
//            saveSale.setInvoiceNumber(saleRequest.getInvoiceNumber());
//            saveSale.setSaleDate(saleRequest.getOrderDate());
//            saveSale.setSubTotal(saleRequest.getSubTotal());
//            saveSale.setPaymentMethod(saleRequest.getPaymentType());
//            saveSale.setTotalAmount(saleRequest.getTotalAmount());
//            saveSale.setLineWiseDiscountTotalAmount(saleRequest.getLineWiseDiscountTotalAmount());
//            saveSale.setBillWiseDiscountPercentage(saleRequest.getBillWiseDiscountPercentage());
//            saveSale.setBillWiseDiscountTotalAmount(saleRequest.getBillWiseDiscountTotalAmount());
//            saveSale.setPaidAmount(saleRequest.getPaidAmount());
//            saveSale.setHold(saleRequest.isHold());
//            saveSale.setFullyPaid(saleRequest.isFullyPaid());
//            saveSale.setStatus(1);
//            saveSale.fillNew(currentUser);
//
//
//            List<SaleProduct> saleProductList = saleRequest.getSoldProducts().stream().map(soldProduct -> {
//                if (soldProduct.getProduct().getProductId() != null) {
//                    Product byProductIdAndSku = productRepository.findByProductIdAndSku(soldProduct.getProduct().getProductId(), soldProduct.getProduct().getSku());
//
//                    if (byProductIdAndSku == null) {
//                        throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
//                    }
//                    // Find and update inventory by SKU and batch number
//                    Inventory inventory = inventoryRepository.findBySkuAndBatchNumber(byProductIdAndSku.getSku(), soldProduct.getProduct().getBatchNo())
//                            .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.INVENTORY_NOT_FOUND));
//
//                    if (!saleRequest.isHold()) {
//                        if (inventory.getQty() > 0) {
//                            inventory.setQty(inventory.getQty() - soldProduct.getProduct().getRemainingQty());
//                            inventoryRepository.save(inventory);
//                        } else {
//                            throw new ServiceException("Quantity is not enough");
//                        }
//                    }
//
//
//                    SaleProduct saleProduct = new SaleProduct();
//                    saleProduct.setSale(saveSale);
//                    saleProduct.setProduct(byProductIdAndSku);
//                    saleProduct.setQuantity(soldProduct.getQuantity());
//                    saleProduct.setDiscountAmount(soldProduct.getDiscountAmount());
//                    saleProduct.setDiscountPercentage(soldProduct.getDiscountPercentage());
//                    saleProduct.setDiscountedTotal(soldProduct.getDiscountedTotal());
//                    saleProduct.setBatchNo(soldProduct.getProduct().getBatchNo());
//                    return saleProduct;
//                } else {
//                    throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
//                }
//            }).toList();
//
//            // Set SaleProducts in the Sale object
//            saveSale.setSaleProducts(saleProductList);
//            // Save the Sale along with SaleProducts in one go
//            Sale savedOrder = saleRepository.save(saveSale);
//
//            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
//            SaleResponse saleResponse = new SaleResponse();
//            saleResponse.setSaleId(savedOrder.getSaleId());
//            saleResponse.setCustomer(savedOrder.getCustomer());
//            saleResponse.setUser(savedOrder.getUser());
//            saleResponse.setSaleDate(sm.format(savedOrder.getSaleDate()));
//            saleResponse.setSubTotal(savedOrder.getSubTotal());
//            saleResponse.setPaymentMethod(savedOrder.getPaymentMethod());
//            saleResponse.setTotalAmount(savedOrder.getTotalAmount());
//            saleResponse.setInvoiceNumber(savedOrder.getInvoiceNumber());
//            List<SoldProductResponse> soldProductResponse = savedOrder.getSaleProducts().stream().map(soldProduct -> {
//                SoldProductResponse response = new SoldProductResponse();
//                response.setSaleProductId(soldProduct.getSaleProductId());
//                response.setProduct(soldProduct.getProduct());
//                response.setSale(soldProduct.getSale());
//                response.setBatchNo(soldProduct.getBatchNo());
//                response.setQuantity(soldProduct.getQuantity());
//                response.setDiscountPercentage(soldProduct.getDiscountPercentage());
//                response.setDiscountAmount(soldProduct.getDiscountAmount());
//                response.setDiscountedTotal(soldProduct.getDiscountedTotal());
//                return response;
//            }).toList();
//            saleResponse.setSoldProducts(soldProductResponse);
//            saleResponse.setStatusCode(SaleConstants.STATUS_201);
//            saleResponse.setDesc(SaleConstants.MESSAGE_201);
//            return saleResponse;
//        } catch (Exception e) {
//            throw new ServiceException("Saving Unsuccessful", e);
//        }
//    }

    @Transactional
    public SaleResponse createSale(SaleRequest saleRequest) {
        try {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

            // Create and populate the Sale entity
            Sale sale = createSaleEntity(saleRequest, currentUser);

            // Process sold products and update inventory
            List<SaleProduct> saleProducts = processSoldProducts(saleRequest, sale);

            // Set SaleProducts and save the Sale
            sale.setSaleProducts(saleProducts);
            Sale savedSale = saleRepository.save(sale);

            // Map the saved Sale to SaleResponse
            return mapToSaleResponse(savedSale);
        } catch (Exception e) {
            throw new ServiceException("Saving Unsuccessful", e);
        }
    }

    private Sale createSaleEntity(SaleRequest saleRequest, String currentUser) {
        Sale sale = new Sale();
        sale.setCustomer(customerMapper.toCustomer(saleRequest.getCustomer()));
        sale.setUser(userMapper.toUser(saleRequest.getUser()));
        sale.setInvoiceNumber(saleRequest.getInvoiceNumber());
        sale.setSaleDate(saleRequest.getOrderDate());
        sale.setSubTotal(saleRequest.getSubTotal());
        sale.setPaymentMethod(saleRequest.getPaymentType());
        sale.setTotalAmount(saleRequest.getTotalAmount());
        sale.setLineWiseDiscountTotalAmount(saleRequest.getLineWiseDiscountTotalAmount());
        sale.setBillWiseDiscountPercentage(saleRequest.getBillWiseDiscountPercentage());
        sale.setBillWiseDiscountTotalAmount(saleRequest.getBillWiseDiscountTotalAmount());
        sale.setPaidAmount(saleRequest.getPaidAmount());
        sale.setHold(saleRequest.isHold());
        sale.setFullyPaid(saleRequest.isFullyPaid());
        sale.setStatus(1);
        sale.fillNew(currentUser);
        return sale;
    }

    private List<SaleProduct> processSoldProducts(SaleRequest saleRequest, Sale sale) {
        return saleRequest.getSoldProducts().stream().map(soldProduct -> {
            Product product = productRepository.findByProductIdAndSku(
                    soldProduct.getProduct().getProductId(),
                    soldProduct.getProduct().getSku()
            );

            if (product == null) {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }

            Batch batch = batchRepository.findByBatchNumberAndSku(
                    soldProduct.getProduct().getBatchNo(),
                    product.getSku()
            ).orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.BATCH_NOT_FOUND));

            if (!saleRequest.isHold() && batch.getQty() < soldProduct.getProduct().getRemainingQty()) {
                throw new ServiceException("Quantity is not enough");
            }

            // Update inventory quantity
            if (!saleRequest.isHold()) {
                batch.setQty(batch.getQty() - soldProduct.getProduct().getRemainingQty());
                batchRepository.save(batch);
            }

            // Create and return SaleProduct
            return createSaleProduct(sale, product, soldProduct);
        }).toList();
    }

    private SaleProduct createSaleProduct(Sale sale, Product product, SoldProductRequest soldProduct) {
        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setSale(sale);
        saleProduct.setProduct(product);
        saleProduct.setQuantity(soldProduct.getQuantity());
        saleProduct.setDiscountAmount(soldProduct.getDiscountAmount());
        saleProduct.setDiscountPercentage(soldProduct.getDiscountPercentage());
        saleProduct.setDiscountedTotal(soldProduct.getDiscountedTotal());
        saleProduct.setBatchNo(soldProduct.getProduct().getBatchNo());
        return saleProduct;
    }

    private SaleResponse mapToSaleResponse(Sale savedSale) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(savedSale.getSaleId());
        saleResponse.setCustomer(savedSale.getCustomer());
        saleResponse.setUser(savedSale.getUser());
        saleResponse.setSaleDate(sm.format(savedSale.getSaleDate()));
        saleResponse.setSubTotal(savedSale.getSubTotal());
        saleResponse.setPaymentMethod(savedSale.getPaymentMethod());
        saleResponse.setTotalAmount(savedSale.getTotalAmount());
        saleResponse.setInvoiceNumber(savedSale.getInvoiceNumber());
        saleResponse.setSoldProducts(savedSale.getSaleProducts().stream().map(this::mapToSoldProductResponse).toList());
        saleResponse.setStatusCode(SaleConstants.STATUS_201);
        saleResponse.setDesc(SaleConstants.MESSAGE_201);
        return saleResponse;
    }

    private SoldProductResponse mapToSoldProductResponse(SaleProduct saleProduct) {
        SoldProductResponse response = new SoldProductResponse();
        response.setSaleProductId(saleProduct.getSaleProductId());
        response.setProduct(saleProduct.getProduct());
        response.setSale(saleProduct.getSale());
        response.setBatchNo(saleProduct.getBatchNo());
        response.setQuantity(saleProduct.getQuantity());
        response.setDiscountPercentage(saleProduct.getDiscountPercentage());
        response.setDiscountAmount(saleProduct.getDiscountAmount());
        response.setDiscountedTotal(saleProduct.getDiscountedTotal());
        return response;
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
            saleResponse.setCustomer(sale.getCustomer());
            saleResponse.setUser(sale.getUser());
            saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
            saleResponse.setTotalAmount(sale.getTotalAmount());
            saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
            List<SoldProductResponse> soldProductResponse = sale.getSaleProducts().stream().map(soldProduct -> {
                SoldProductResponse response = new SoldProductResponse();
                response.setSaleProductId(soldProduct.getSaleProductId());
                response.setProduct(soldProduct.getProduct());
                response.setSale(soldProduct.getSale());
                response.setBatchNo(soldProduct.getBatchNo());
                response.setQuantity(soldProduct.getQuantity());
                response.setDiscountPercentage(soldProduct.getDiscountPercentage());
                response.setDiscountAmount(soldProduct.getDiscountAmount());
                response.setDiscountedTotal(soldProduct.getDiscountedTotal());
                return response;
            }).toList();
            saleResponse.setSoldProducts(soldProductResponse);
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
