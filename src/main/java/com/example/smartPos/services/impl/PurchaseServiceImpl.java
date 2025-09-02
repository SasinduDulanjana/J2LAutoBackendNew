package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.ProductBatchRequest;
import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.ProductBatchResponse;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.*;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.IPurchaseService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.ProductConstants;
import org.springframework.security.core.context.SecurityContextHolder;
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
            purchResp.setTotalCost(purchase.getTotalCost());
            purchResp.setPaidAmount(purchase.getPaidAmount());
            purchResp.setProducts(purchase.getProducts().stream().map(product -> {
                ProductResponse productResponse = new ProductResponse();
                productResponse.setProductId(product.getProductId());
                productResponse.setSku(product.getSku());
                productResponse.setProductName(product.getProductName());
//                productResponse.setSalePrice(product.getSalePrice());
//                productResponse.setWholeSalePrice(product.getWholeSalePrice());
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

        Purchase savedPurchase = getSavedPurchase(purchaseRequest);

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
        // Retrieve the currently authenticated user's username
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Purchase purchase = new Purchase();
        purchase.setSupplierId(purchaseRequest.getSupId());
        purchase.setPurchaseName(purchaseRequest.getPurchaseName());
        purchase.setInvoiceNumber(purchaseRequest.getInvoiceNumber());
        purchase.setDeliveryTime(purchaseRequest.getDeliveryTime());
        purchase.setInvoiceDate(purchaseRequest.getInvoiceDate());
        purchase.setConnectionStatus(purchaseRequest.getConnectionStatus());
        purchase.setPaymentStatus(purchaseRequest.getPaymentStatus());
        purchase.setProductType(purchaseRequest.getProductType());
        purchase.setStatus(1);
        purchase.fillNew(currentUser);
        List<Product> productlist = purchaseRequest.getProducts().stream().map(productRequest -> {
            Product byProductIdAndSku = new Product();
            if (productRequest.getProductId() != null) {
                byProductIdAndSku = productRepository.findByProductIdAndSku(productRequest.getProductId(), productRequest.getSku());
            }
            return byProductIdAndSku;
        }).toList();
        purchase.setProducts(productlist);
        purchase.setTotalCost(purchaseRequest.getTotalCost());
        purchase.setPaidAmount(purchaseRequest.getPaidAmount());
        purchase.setFullyPaid(purchaseRequest.getFullyPaid());
        Purchase savedPurchase = purchaseRepository.save(purchase);

        List<Product> products = purchaseRequest.getProducts().stream().map(product -> {
            if (product.getProductId() != null) {
                Product byProductIdAndSku = productRepository.findByProductIdAndSku(product.getProductId(), product.getSku());

                // Update Purchased products details with batches
                ProductBatch productBatch = new ProductBatch();
                productBatch.setProduct(byProductIdAndSku);
                productBatch.setQty(product.getRemainingQty());
                productBatch.setUnitCost(product.getCost());
                productBatch.setPurchaseId(savedPurchase.getPurchaseId());
                productBatch.setInvoiceNumber(purchaseRequest.getInvoiceNumber());
                productBatch.setPurchaseDate(purchaseRequest.getInvoiceDate());
                productBatch.setSupId(purchaseRequest.getSupId());
                productBatch.setRetailPrice(product.getRetailPrice());
                productBatch.fillNew(currentUser);

                //Check Batch Number is already exist
                Optional<Batch> batchByBatchNumberAndSku = batchRepository.findByBatchNumberAndSku(product.getBatchNo(), byProductIdAndSku.getSku());
                if (batchByBatchNumberAndSku.isEmpty()) {
                    //Save Batch Details
                    Batch batch = new Batch();
                    batch.setSku(byProductIdAndSku.getSku());
                    batch.setBatchNumber(product.getBatchNo());
                    batch.setUnitCost(product.getCost());
                    batch.setRetailPrice(product.getRetailPrice());
                    batch.setQty(product.getRemainingQty());
                    batch.setWholesalePrice(product.getWholeSalePrice());
                    batch.fillNew(currentUser);
                    Batch savedBatch = batchRepository.save(batch);
                    // Update batch into purchase details
                    productBatch.setBatch(savedBatch);
                } else {
                    // Update batch into purchase details
                    Batch exisitingBatch = batchByBatchNumberAndSku.get();
                    exisitingBatch.setQty((exisitingBatch.getQty() == null ? 0 : exisitingBatch.getQty()) + product.getRemainingQty());
                    exisitingBatch.fillUpdated(currentUser);
                    productBatch.setBatch(exisitingBatch);
                }
                productBatchRepository.save(productBatch);

                // Update inventory
                Optional<Inventory> inventoryBySkuAndBatchId = inventoryRepository.findBySkuAndBatchNumber(byProductIdAndSku.getSku(), product.getBatchNo());
                //if inventory already exist update the qty
                if (inventoryBySkuAndBatchId.isPresent()) {
                    inventoryBySkuAndBatchId.get().setQty((inventoryBySkuAndBatchId.get().getQty() == null ? 0 : inventoryBySkuAndBatchId.get().getQty()) + product.getRemainingQty());
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
        return purchase;
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
//            productResponse.setSalePrice(product.getSalePrice());
//            productResponse.setWholeSalePrice(product.getWholeSalePrice());
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

    @Override
    public ProductBatchResponse fetchProductBatchDetails(ProductBatchRequest request) {
        ProductBatch productBatch = productBatchRepository.findByPurchaseIdAndProduct_ProductId(request.getPurchaseId(), request.getProductId());

        if (productBatch == null) {
            throw new ResourceNotFoundException(ErrorCodes.PRODUCT_BATCH_NOT_FOUND);
        }

        Batch batch = batchRepository.findById(productBatch.getBatch().getBatchId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.BATCH_NOT_FOUND)
        );

        ProductBatchResponse response = new ProductBatchResponse();
        response.setPurchaseId(productBatch.getPurchaseId());
        response.setBatchNumber(batch.getBatchNumber());
        response.setProductId(productBatch.getProduct().getProductId());
        response.setQty(productBatch.getQty());
        response.setUnitCost(productBatch.getUnitCost());
        response.setRetailPrice(productBatch.getRetailPrice());
        return response;
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
