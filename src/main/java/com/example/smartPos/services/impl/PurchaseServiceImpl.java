package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.*;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.IPurchaseService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.ProductConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final SupplierRepository supplierRepository;

    private final BatchRepository batchRepository;

    private final InventoryRepository inventoryRepository;

    private final ProductBatchRepository productBatchRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository, SupplierRepository supplierRepository, BatchRepository batchRepository, InventoryRepository inventoryRepository, ProductBatchRepository productBatchRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.batchRepository = batchRepository;
        this.inventoryRepository = inventoryRepository;
        this.productBatchRepository = productBatchRepository;
    }

    @Override
    public List<PurchaseResponse> getAllPurchases() {

        return purchaseRepository.findAll().stream().map(purchase -> {
            PurchaseResponse purchResp = new PurchaseResponse();
            purchResp.setPurchaseId(purchase.getPurchaseId());
            purchResp.setSupplierId(purchase.getSupplierId());
            purchResp.setPurchaseName(purchase.getPurchaseName());
            purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
            purchResp.setDeliveryTime(purchase.getDeliveryTime());
            purchResp.setInvoiceDate(purchase.getInvoiceDate());
            purchResp.setConnectionStatus(purchase.getConnectionStatus());
            purchResp.setPaymentStatus(purchase.getPaymentStatus());
            purchResp.setProductType(purchase.getProductType());
            purchResp.setProducts(purchase.getProducts().stream().map(product -> {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setProductId(product.getProductId());
                productResponse.setSku(product.getSku());
                productResponse.setProductName(product.getProductName());
                productResponse.setSalePrice(product.getSalePrice());
                productResponse.setWholeSalePrice(product.getWholeSalePrice());
                productResponse.setLowQty(product.getLowQty());
                productResponse.setBatchNo(product.getBatchNo());
                return productResponse;
            }).toList());
            return purchResp;
        }).toList();
    }

    @Override
    public List<PurchaseResponse> getAllByPurchaseName(String purchaseName) {
        return purchaseRepository.findAllByPurchaseName(purchaseName).stream().map(purchase -> {
            PurchaseResponse purchResp = new PurchaseResponse();
            purchResp.setPurchaseId(purchase.getPurchaseId());
            purchResp.setSupplierId(purchase.getSupplierId());
            purchResp.setPurchaseName(purchase.getPurchaseName());
            purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
            purchResp.setDeliveryTime(purchase.getDeliveryTime());
            purchResp.setInvoiceDate(purchase.getInvoiceDate());
            purchResp.setConnectionStatus(purchase.getConnectionStatus());
            purchResp.setPaymentStatus(purchase.getPaymentStatus());
            purchResp.setProductType(purchase.getProductType());
            purchResp.setStatus(purchase.getStatus());
            return purchResp;
        }).toList();
    }

    @Override
    public List<PurchaseResponse> getAllBySupplierId(Integer supplierId) {
        return purchaseRepository.findAllBySupplierId(supplierId).stream().map(purchase -> {
            PurchaseResponse purchResp = new PurchaseResponse();
            purchResp.setPurchaseId(purchase.getPurchaseId());
            purchResp.setSupplierId(purchase.getSupplierId());
            purchResp.setPurchaseName(purchase.getPurchaseName());
            purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
            purchResp.setDeliveryTime(purchase.getDeliveryTime());
            purchResp.setInvoiceDate(purchase.getInvoiceDate());
            purchResp.setConnectionStatus(purchase.getConnectionStatus());
            purchResp.setPaymentStatus(purchase.getPaymentStatus());
            purchResp.setProductType(purchase.getProductType());
            purchResp.setStatus(purchase.getStatus());
            return purchResp;
        }).toList();
    }

    @Override
    public List<PurchaseResponse> getAllByInvoiceDate(String date) {
        return purchaseRepository.findAllByInvoiceDate(date).stream().map(purchase -> {
            PurchaseResponse purchResp = new PurchaseResponse();
            purchResp.setPurchaseId(purchase.getPurchaseId());
            purchResp.setSupplierId(purchase.getSupplierId());
            purchResp.setPurchaseName(purchase.getPurchaseName());
            purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
            purchResp.setDeliveryTime(purchase.getDeliveryTime());
            purchResp.setInvoiceDate(purchase.getInvoiceDate());
            purchResp.setConnectionStatus(purchase.getConnectionStatus());
            purchResp.setPaymentStatus(purchase.getPaymentStatus());
            purchResp.setProductType(purchase.getProductType());
            purchResp.setStatus(purchase.getStatus());
            return purchResp;
        }).toList();
    }

    @Override
    public List<PurchaseResponse> getAllByPaymentStatus(String status) {
        return purchaseRepository.findAllByPaymentStatus(status).stream().map(purchase -> {
            PurchaseResponse purchResp = new PurchaseResponse();
            purchResp.setPurchaseId(purchase.getPurchaseId());
            purchResp.setSupplierId(purchase.getSupplierId());
            purchResp.setPurchaseName(purchase.getPurchaseName());
            purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
            purchResp.setDeliveryTime(purchase.getDeliveryTime());
            purchResp.setInvoiceDate(purchase.getInvoiceDate());
            purchResp.setConnectionStatus(purchase.getConnectionStatus());
            purchResp.setPaymentStatus(purchase.getPaymentStatus());
            purchResp.setProductType(purchase.getProductType());
            purchResp.setStatus(purchase.getStatus());
            return purchResp;
        }).toList();
    }

    @Override
    public PurchaseResponse getPurchaseByInvoiceNumber(String invoiceNumber) {
        Purchase purchase = purchaseRepository.findByInvoiceNumber(invoiceNumber).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.INVOICE_NUMBER_NOT_FOUND)
        );
        PurchaseResponse purchResp = new PurchaseResponse();
        purchResp.setPurchaseId(purchase.getPurchaseId());
        purchResp.setSupplierId(purchase.getSupplierId());
        purchResp.setPurchaseName(purchase.getPurchaseName());
        purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
        purchResp.setDeliveryTime(purchase.getDeliveryTime());
        purchResp.setInvoiceDate(purchase.getInvoiceDate());
        purchResp.setConnectionStatus(purchase.getConnectionStatus());
        purchResp.setPaymentStatus(purchase.getPaymentStatus());
        purchResp.setProductType(purchase.getProductType());
        purchResp.setStatus(purchase.getStatus());
        return purchResp;

    }

    @Override
    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest) {
        Optional<Purchase> purchaseByInvNum = purchaseRepository.findByInvoiceNumber(purchaseRequest.getInvoiceNumber());
        if (purchaseByInvNum.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_INVOICE_NUMBER);
        }

        Optional<Supplier> supplierById = supplierRepository.findById(purchaseRequest.getSupId());
        if (supplierById.isEmpty()) {
            throw new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        Purchase savedPurchase = purchaseRepository.save(getSavedPurchase(purchaseRequest));

        PurchaseResponse purchaseResponse = new PurchaseResponse();
        purchaseResponse.setPurchaseId(savedPurchase.getPurchaseId());
        purchaseResponse.setSupplierId(savedPurchase.getSupplierId());
        purchaseResponse.setInvoiceNumber(savedPurchase.getInvoiceNumber());
        purchaseResponse.setProducts(savedPurchase.getProducts().stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(product.getProductId());
            return productResponse;
        }).toList());
        purchaseResponse.setStatusCode(ProductConstants.STATUS_201);
        purchaseResponse.setDesc(ProductConstants.MESSAGE_201);
        return purchaseResponse;
    }


    private Purchase getSavedPurchase(PurchaseRequest purchaseRequest) {
        Purchase savePurchase = new Purchase();
        savePurchase.setSupplierId(purchaseRequest.getSupId());
        savePurchase.setPurchaseName(purchaseRequest.getPurchaseName());
        savePurchase.setInvoiceNumber(purchaseRequest.getInvoiceNumber());
        savePurchase.setDeliveryTime(purchaseRequest.getDeliveryTime());
        savePurchase.setInvoiceDate(purchaseRequest.getInvoiceDate());
        savePurchase.setConnectionStatus(purchaseRequest.getConnectionStatus());
        savePurchase.setPaymentStatus(purchaseRequest.getPaymentStatus());
        savePurchase.setProductType(purchaseRequest.getProductType());
        savePurchase.setStatus(1);
        savePurchase.fillNew("ADMIN USER");
        List<Product> products = purchaseRequest.getProducts().stream().map(product -> {
            if (product.getProductId() != null) {
                Product byProductIdAndSku = productRepository.findByProductIdAndSku(product.getProductId(), product.getSku());

                // Update Purchased products details with batches
                ProductBatch productBatch = new ProductBatch();
                productBatch.setProduct(byProductIdAndSku);
                productBatch.setQty(product.getRemainingQty());
                productBatch.setUnitCost(product.getCost());

                //Check Batch Number is already exist
                Optional<Batch> batchByBatchNumberAndSku = batchRepository.findByBatchNumberAndSku(product.getBatchNo(), byProductIdAndSku.getSku());
                if (batchByBatchNumberAndSku.isEmpty()) {
                    //Save Batch Details
                    Batch batch = new Batch();
                    batch.setSku(byProductIdAndSku.getSku());
                    batch.setBatchNumber(product.getBatchNo());
                    batch.setUnitCost(product.getCost());
                    batch.setRetailPrice(product.getRetailPrice());
                    batch.setWholesalePrice(product.getWholeSalePrice());
                    batch.setSupplier(purchaseRequest.getSupId());
                    batch.setPurchaseDate(purchaseRequest.getInvoiceDate());
                    batch.setInvoiceNumber(purchaseRequest.getInvoiceNumber());
                    batch.fillNew("ADMIN USER");
                    Batch savedBatch = batchRepository.save(batch);
                    // Update batch into purchase details
                    productBatch.setBatch(savedBatch);
                }else{
                    // Update batch into purchase details
                    productBatch.setBatch(batchByBatchNumberAndSku.get());
                }
                productBatchRepository.save(productBatch);

                // Update inventory
                Optional<Inventory> inventoryBySkuAndBatchId = inventoryRepository.findBySkuAndBatchNumber(byProductIdAndSku.getSku(), product.getBatchNo());
                //if inventory already exist update the qty
                if (inventoryBySkuAndBatchId.isPresent()) {
                    inventoryBySkuAndBatchId.get().setQty(inventoryBySkuAndBatchId.get().getQty() + product.getRemainingQty());
                } else {
                    //if inventory not exist create new inventory
                    Inventory inventory = new Inventory();
                    inventory.setSku(byProductIdAndSku.getSku());
                    inventory.setBatchNumber(product.getBatchNo());
                    inventory.setQty(product.getRemainingQty());
                    inventoryRepository.save(inventory);
                }
                return byProductIdAndSku;
            } else {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }
        }).toList();
        savePurchase.setProducts(products);
        savePurchase.setTotalCost(purchaseRequest.getTotalCost());
        savePurchase.setPaidAmount(purchaseRequest.getPaidAmount());
        savePurchase.setFullyPaid(purchaseRequest.getFullyPaid());
        return savePurchase;
    }

    @Override
    public PurchaseResponse getPurchaseById(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findByIdWithProducts(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PURCHASE_NOT_FOUND));

        PurchaseResponse purchaseResponse = new PurchaseResponse();
        purchaseResponse.setPurchaseId(purchase.getPurchaseId());
        purchaseResponse.setSupplierId(purchase.getSupplierId());
        purchaseResponse.setPurchaseName(purchase.getPurchaseName());
        purchaseResponse.setInvoiceNumber(purchase.getInvoiceNumber());
        purchaseResponse.setDeliveryTime(purchase.getDeliveryTime());
        purchaseResponse.setInvoiceDate(purchase.getInvoiceDate());
        purchaseResponse.setConnectionStatus(purchase.getConnectionStatus());
        purchaseResponse.setPaymentStatus(purchase.getPaymentStatus());
        purchaseResponse.setProductType(purchase.getProductType());
        purchaseResponse.setStatus(purchase.getStatus());
        purchaseResponse.setProducts(purchase.getProducts().stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(product.getProductId());
            productResponse.setProductName(product.getProductName());
            productResponse.setSku(product.getSku());
            productResponse.setSalePrice(product.getSalePrice());
            productResponse.setWholeSalePrice(product.getWholeSalePrice());
            productResponse.setLowQty(product.getLowQty());
            productResponse.setBatchNo(product.getBatchNo());
            return productResponse;
        }).toList());
        return purchaseResponse;
    }

    @Override
    public List<PurchaseResponse> getPurchasesByDateRange(Date startDate, Date endDate) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
        return purchaseRepository.findAllByAddedDateBetweenAndStatusNot(startDate, endDate).stream().map(purchase -> {
            PurchaseResponse purchaseResponse = new PurchaseResponse();
            purchaseResponse.setPurchaseId(purchase.getPurchaseId());
            purchaseResponse.setSupplierId(purchase.getSupplierId());
            // Fetch supplier name using supplierId
            Optional<Supplier> supplier = supplierRepository.findById(purchase.getSupplierId());
            supplier.ifPresent(value -> purchaseResponse.setSupplierName(value.getName()));
            purchaseResponse.setPurchaseName(purchase.getPurchaseName());
            purchaseResponse.setInvoiceNumber(purchase.getInvoiceNumber());
            purchaseResponse.setDeliveryTime(purchase.getDeliveryTime());
            purchaseResponse.setInvoiceDate(purchase.getInvoiceDate());
            purchaseResponse.setConnectionStatus(purchase.getConnectionStatus());
            purchaseResponse.setPaymentStatus(purchase.getPaymentStatus());
            purchaseResponse.setProductType(purchase.getProductType());
            purchaseResponse.setStatus(purchase.getStatus());
            purchaseResponse.setProducts(purchase.getProducts().stream().map(product -> {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setProductId(product.getProductId());
                return productResponse;
            }).toList());
            purchaseResponse.setTotalCost(purchase.getTotalCost());
            purchaseResponse.setPaidAmount(purchase.getPaidAmount());
            purchaseResponse.setFullyPaid(purchase.getFullyPaid());
            return purchaseResponse;
        }).toList();
    }

//    @Override
//    public PurchaseResponse updatePurchase(PurchaseRequest purchaseRequest) {
//        PurchaseResponse updatedPurchaseResponse = new PurchaseResponse();
//        if (purchaseRequest.getPurchaseId() != null) {
//            Purchase purchase = purchaseRepository.findById(purchaseRequest.getPurchaseId()).orElseThrow(
//                    () -> new ResourceNotFoundException(ErrorCodes.PURCHASE_NOT_FOUND)
//            );
//            purchase.setSupplierId(purchaseRequest.getSupplierId());
//            purchase.setPurchaseName(purchaseRequest.getPurchaseName());
//            purchase.setInvoiceNumber(purchaseRequest.getInvoiceNumber());
//            purchase.setDeliveryTime(purchaseRequest.getDeliveryTime());
//            purchase.setInvoiceDate(purchaseRequest.getInvoiceDate());
//            purchase.setConnectionStatus(purchaseRequest.getConnectionStatus());
//            purchase.setPaymentStatus(purchaseRequest.getPaymentStatus());
//            purchase.setProductType(purchaseRequest.getProductType());
//            purchase.setStatus(1);
//            purchase.fillUpdated("ADMIN USER");
//            List<Product> products = purchaseRequest.getProducts().stream().map(product -> {
//                if (product.getProductId() != null) {
//                    Product byProductIdAndSku = productRepository.findByProductIdAndSku(product.getProductId(), product.getSku());
//
//                    Double reducedQty = byProductIdAndSku.getRemainingQty()-product.getExistingQty();
//                    //Stock management logic
//                    Double currentTotalQty = reducedQty + product.getRemainingQty();
//                    Double remainingTotalCost = byProductIdAndSku.getCost() * byProductIdAndSku.getRemainingQty();
//                    Double currentTotalCost = product.getCost() * product.getRemainingQty();
//                    Double totalCost = remainingTotalCost + currentTotalCost;
//                    Double CurrentUnitPrice = totalCost / currentTotalQty;
//                    byProductIdAndSku.setCost(CurrentUnitPrice);
//                    byProductIdAndSku.setRemainingQty(currentTotalQty);
//                    productRepository.save(byProductIdAndSku);
//
//                    return byProductIdAndSku;
//                } else {
//                    throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
//                }
//            }).toList();
//            purchase.setProducts(products);
//            Purchase updatePurchase = purchaseRepository.save(purchase);
//
//            updatedPurchaseResponse.setPurchaseId(updatePurchase.getPurchaseId());
//            updatedPurchaseResponse.setInvoiceNumber(updatePurchase.getInvoiceNumber());
//            updatedPurchaseResponse.setProducts(updatePurchase.getProducts());
//
//        }
//        return updatedPurchaseResponse;
//    }
}
