package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.PurchaseRepository;
import com.example.smartPos.repositories.SupplierRepository;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Purchase;
import com.example.smartPos.repositories.model.Supplier;
import com.example.smartPos.services.IPurchaseService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.ProductConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final SupplierRepository supplierRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
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
            purchResp.setStatus(purchase.getStatus());
            purchResp.setProducts(purchase.getProducts());
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
            purchResp.setProducts(purchase.getProducts());
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
            purchResp.setProducts(purchase.getProducts());
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
            purchResp.setProducts(purchase.getProducts());
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
            purchResp.setProducts(purchase.getProducts());
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
        purchResp.setProducts(purchase.getProducts());
        return purchResp;

    }

    @Override
    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest) {
        Optional<Purchase> purchaseByInvNum = purchaseRepository.findByInvoiceNumber(purchaseRequest.getInvoiceNumber());
        if (purchaseByInvNum.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_INVOICE_NUMBER);
        }

        Optional<Supplier> supplierById = supplierRepository.findById(purchaseRequest.getSupplierId());
        if (supplierById.isPresent()) {
            throw new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        Purchase savedPurchase = purchaseRepository.save(getSavedPurchase(purchaseRequest));

        PurchaseResponse purchaseResponse = new PurchaseResponse();
        purchaseResponse.setPurchaseId(savedPurchase.getPurchaseId());
        purchaseResponse.setSupplierId(savedPurchase.getSupplierId());
        purchaseResponse.setInvoiceNumber(savedPurchase.getInvoiceNumber());
        purchaseResponse.setProducts(savedPurchase.getProducts());
        purchaseResponse.setStatusCode(ProductConstants.STATUS_201);
        purchaseResponse.setDesc(ProductConstants.MESSAGE_201);
        return purchaseResponse;
    }


    private Purchase getSavedPurchase(PurchaseRequest purchaseRequest) {
        Purchase savePurchase = new Purchase();
        savePurchase.setSupplierId(purchaseRequest.getSupplierId());
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

                //Stock management logic

                Double currentTotalQty = byProductIdAndSku.getRemainingQty() + product.getRemainingQty();
                Double remainingTotalCost = byProductIdAndSku.getCost() * byProductIdAndSku.getRemainingQty();
                Double currentTotalCost = product.getCost() * product.getRemainingQty();
                Double totalCost = remainingTotalCost + currentTotalCost;
                Double CurrentUnitPrice = totalCost / currentTotalQty;
                byProductIdAndSku.setCost(CurrentUnitPrice);
                byProductIdAndSku.setRemainingQty(currentTotalQty);
                productRepository.save(byProductIdAndSku);

                return byProductIdAndSku;
            } else {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }
        }).toList();
        savePurchase.setProducts(products);
        return savePurchase;
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
