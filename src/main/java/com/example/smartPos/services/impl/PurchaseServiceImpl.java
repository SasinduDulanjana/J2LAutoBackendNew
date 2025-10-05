package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.*;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.mapper.PaymentDetailsMapper;
import com.example.smartPos.mapper.PurchaseMapper;
import com.example.smartPos.mapper.PurchaseReturnMapper;
import com.example.smartPos.repositories.*;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.IPurchaseService;
import com.example.smartPos.util.*;
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
public class PurchaseServiceImpl implements IPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;

    private final SupplierRepository supplierRepository;

    private final BatchRepository batchRepository;

    private final ProductBatchRepository productBatchRepository;

    private final PurchaseReturnRepository purchaseReturnRepository;
    private final PurchaseMapper purchaseMapper;

    private final PurchaseReturnMapper purchaseReturnMapper;

    private final PaymentRepository paymentRepository;

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final PaymentDetailsMapper paymentDetailsMapper;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ProductRepository productRepository, SupplierRepository supplierRepository, BatchRepository batchRepository, ProductBatchRepository productBatchRepository, PurchaseReturnRepository purchaseReturnRepository, PurchaseMapper purchaseMapper, PurchaseReturnMapper purchaseReturnMapper, PaymentRepository paymentRepository, PaymentDetailsRepository paymentDetailsRepository, PaymentDetailsMapper paymentDetailsMapper) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.batchRepository = batchRepository;
        this.productBatchRepository = productBatchRepository;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.purchaseMapper = purchaseMapper;
        this.purchaseReturnMapper = purchaseReturnMapper;
        this.paymentRepository = paymentRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.paymentDetailsMapper = paymentDetailsMapper;
    }

//    @Override
//    public List<PurchaseResponse> getAllPurchases() {
//
//        List<Purchase> purchaseList = purchaseRepository.findAll();
//
//        // Fetch all payment details for the sales in a single query
//        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findByInvoiceNumbersAndPaymentPaymentTypeAndPurchasePaymentType(
//                purchaseList.stream().map(purchase -> purchase.getPurchaseId().toString()).toList()
//        );
//
//        // Group payment details by sale ID
//        Map<String, Double> totalPaidAmountByPurchaseId = paymentDetailsList.stream()
//                .collect(Collectors.groupingBy(
//                        pd -> pd.getPayment().getReferenceId(),
//                        Collectors.summingDouble(PaymentDetails::getAmount)
//                ));
//
//
//        return purchaseList.stream().map(purchase -> {
//            Double totalPaidAmount = totalPaidAmountByPurchaseId.getOrDefault(purchase.getPurchaseId().toString(), 0.0);
//            Double outstandingBalance = purchase.getTotalCost() - totalPaidAmount;
//
//            PurchaseResponse purchResp = new PurchaseResponse();
//            purchResp.setPurchaseId(purchase.getPurchaseId());
//            purchResp.setSupplierId(purchase.getSupplierId());
//            purchResp.setPurchaseName(purchase.getPurchaseName());
//            purchResp.setInvoiceNumber(purchase.getInvoiceNumber());
//            purchResp.setDeliveryTime(purchase.getDeliveryTime());
//            purchResp.setInvoiceDate(purchase.getInvoiceDate());
//            purchResp.setConnectionStatus(purchase.getConnectionStatus());
//            purchResp.setPaymentStatus(purchase.getPaymentStatus());
//            purchResp.setProductType(purchase.getProductType());
//            purchResp.setTotalCost(purchase.getTotalCost());
//            purchResp.setPaidAmount(purchase.getPaidAmount());
//            purchResp.setOutstandingAmount(outstandingBalance);
//            return purchResp;
//        }).toList();
//    }

    @Override
    public List<PurchaseResponse> getAllPurchases() {
        List<Purchase> purchaseList = purchaseRepository.findAll();

        // Fetch all payment details for the purchases in a single query
        Map<Integer, Double> totalPaidAmountByPurchaseId = paymentDetailsRepository
                .findByInvoiceNumbersAndPaymentPaymentTypeAndPurchasePaymentType(
                        purchaseList.stream().map(purchase -> purchase.getPurchaseId().toString()).toList()
                )
                .stream()
                .collect(Collectors.groupingBy(
                        pd -> Integer.parseInt(pd.getPayment().getReferenceId()),
                        Collectors.summingDouble(PaymentDetails::getAmount)
                ));

        // Map purchases to responses
        return purchaseList.stream()
                .map(purchase -> mapToPurchaseResponse(purchase, totalPaidAmountByPurchaseId))
                .toList();
    }

    private PurchaseResponse mapToPurchaseResponse(Purchase purchase, Map<Integer, Double> totalPaidAmountByPurchaseId) {
        double totalPaidAmount = totalPaidAmountByPurchaseId.getOrDefault(purchase.getPurchaseId(), 0.0);
        double outstandingBalance = purchase.getTotalCost() - totalPaidAmount;
//        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        PurchaseResponse response = new PurchaseResponse();
        response.setPurchaseId(purchase.getPurchaseId());
        response.setSupplierId(purchase.getSupplierId());
        response.setPurchaseName(purchase.getPurchaseName());
        response.setInvoiceNumber(purchase.getInvoiceNumber());
        response.setDeliveryTime(purchase.getDeliveryTime());
        response.setInvoiceDate(purchase.getInvoiceDate());
        response.setConnectionStatus(purchase.getConnectionStatus());
        response.setPaymentStatus(purchase.getPaymentStatus());
        response.setProductType(purchase.getProductType());
        response.setTotalCost(purchase.getTotalCost());
        response.setPaidAmount(totalPaidAmount);
        response.setOutstandingAmount(outstandingBalance);
        return response;
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
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Purchase> purchaseByInvNum = purchaseRepository.findByInvoiceNumber(purchaseRequest.getInvoiceNumber());
        if (purchaseByInvNum.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_INVOICE_NUMBER);
        }

        Optional<Supplier> supplierById = supplierRepository.findById(purchaseRequest.getSupId());
        if (supplierById.isEmpty()) {
            throw new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND);
        }

        Purchase savedPurchase = getSavedPurchase(purchaseRequest, currentUser);

        // Create and save payment
        Payment payment = createPaymentEntity(purchaseRequest, savedPurchase, currentUser);
        paymentRepository.save(payment);

        // Create and save payment details
        PaymentDetails paymentDetails = createPaymentDetailsEntities(purchaseRequest, payment, currentUser);
        paymentDetailsRepository.save(paymentDetails);

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

    private Payment createPaymentEntity(PurchaseRequest purchaseRequest, Purchase savedPurchase, String currentUser) {
        Payment payment = new Payment();
        payment.setReferenceId(savedPurchase.getPurchaseId().toString());
        payment.setPaymentDate(new Date());
        Supplier supplier = supplierRepository.findById(savedPurchase.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND));
        payment.setSupplier(supplier);
        payment.setTotalAmount(purchaseRequest.getTotalCost());
        payment.setPaymentType(PaymentType.PAYMENT);
        payment.setReferenceType(ReferenceType.PURCHASE);
        payment.setRemarks(savedPurchase.getInvoiceNumber());
        payment.fillNew(currentUser);
        return payment;
    }

    private PaymentDetails createPaymentDetailsEntities(PurchaseRequest purchaseRequest, Payment payment, String currentUser) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPayment(payment);
        paymentDetails.setPaymentMethod(purchaseRequest.getPaymentType());
        paymentDetails.setAmount(purchaseRequest.getPaidAmount());
        paymentDetails.setChequeNo(purchaseRequest.getChequeNumber());
        paymentDetails.setBankName(purchaseRequest.getBankName());
        paymentDetails.setChequeDate(purchaseRequest.getChequeDate());
        paymentDetails.setPaymentDate(new Date());
        if (purchaseRequest.getPaymentType().equalsIgnoreCase("CHEQUE")) {
            paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            paymentDetails.setPaymentStatus(PaymentStatus.CLEARED);
        }
        paymentDetails.fillNew(currentUser);

        return paymentDetails;
    }


    @Transactional
    private Purchase getSavedPurchase(PurchaseRequest purchaseRequest, String currentUser) {


        // Create and populate the Purchase object
        Purchase purchase = createPurchaseEntity(purchaseRequest, currentUser);

        // Fetch all products in bulk
        List<Product> products = fetchProducts(purchaseRequest);

        // Set products and save the purchase
        purchase.setProducts(products);
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Process batches and inventory
        processBatchesAndInventory(purchaseRequest, products, savedPurchase, currentUser);

        return savedPurchase;
    }

    private Purchase createPurchaseEntity(PurchaseRequest purchaseRequest, String currentUser) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Purchase purchase = new Purchase();
        purchase.setSupplierId(purchaseRequest.getSupId());
        purchase.setPurchaseName(purchaseRequest.getPurchaseName());
        purchase.setInvoiceNumber(purchaseRequest.getInvoiceNumber());
        purchase.setDeliveryTime(purchaseRequest.getDeliveryTime());
        purchase.setInvoiceDate(sm.format(new Date()));
        purchase.setConnectionStatus(purchaseRequest.getConnectionStatus());
        purchase.setPaymentStatus(purchaseRequest.getPaymentStatus());
        purchase.setProductType(purchaseRequest.getProductType());
        purchase.setStatus(1);
        purchase.fillNew(currentUser);
        purchase.setTotalCost(purchaseRequest.getTotalCost());
        purchase.setPaidAmount(purchaseRequest.getPaidAmount());
        purchase.setFullyPaid(purchaseRequest.getFullyPaid());
        purchase.setPaymentType(purchaseRequest.getPaymentType());
        purchase.setChequeNo(purchaseRequest.getChequeNumber());
        return purchase;
    }

    private List<Product> fetchProducts(PurchaseRequest purchaseRequest) {
        return purchaseRequest.getProducts().stream()
                .map(productRequest -> {
                    if (productRequest.getProductId() != null) {
                        return productRepository.findByProductIdAndSku(productRequest.getProductId(), productRequest.getSku());
                    } else {
                        throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
                    }
                })
                .toList();
    }

    private void processBatchesAndInventory(PurchaseRequest purchaseRequest, List<Product> products, Purchase savedPurchase, String currentUser) {
        purchaseRequest.getProducts().forEach(productRequest -> {
            Product product = products.stream()
                    .filter(p -> p.getProductId().equals(productRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND));

            // Process ProductBatch
            ProductBatch productBatch = createProductBatch(productRequest, product, savedPurchase, currentUser);
            productBatchRepository.save(productBatch);

            // Process Batch
            Batch batch = processBatch(productRequest, product, currentUser);
            productBatch.setBatch(batch);
        });
    }

    private ProductBatch createProductBatch(ProductRequest productRequest, Product product, Purchase savedPurchase, String currentUser) {
        ProductBatch productBatch = new ProductBatch();
        productBatch.setProduct(product);
        productBatch.setQty(productRequest.getRemainingQty());
        productBatch.setUnitCost(productRequest.getCost());
        productBatch.setPurchaseId(savedPurchase.getPurchaseId());
        productBatch.setInvoiceNumber(savedPurchase.getInvoiceNumber());
        productBatch.setPurchaseDate(savedPurchase.getInvoiceDate());
        productBatch.setSupId(savedPurchase.getSupplierId());
        productBatch.setRetailPrice(productRequest.getRetailPrice());
        productBatch.fillNew(currentUser);
        return productBatch;
    }

    private Batch processBatch(ProductRequest productRequest, Product product, String currentUser) {
        Optional<Batch> existingBatch = batchRepository.findByBatchNumberAndSku(productRequest.getBatchNo(), product.getSku());
        if (existingBatch.isPresent()) {
            Batch batch = existingBatch.get();
            batch.setQty((batch.getQty() == null ? 0 : batch.getQty()) + productRequest.getRemainingQty());
            batch.fillUpdated(currentUser);
            return batch;
        } else {
            Batch batch = new Batch();
            batch.setSku(product.getSku());
            batch.setBatchNumber(productRequest.getBatchNo());
            batch.setUnitCost(productRequest.getCost());
            batch.setRetailPrice(productRequest.getRetailPrice());
            batch.setQty(productRequest.getRemainingQty());
            batch.setWholesalePrice(productRequest.getWholeSalePrice());
            batch.fillNew(currentUser);
            return batchRepository.save(batch);
        }
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
        // Fetch ProductBatch and validate
        ProductBatch productBatch = Optional.ofNullable(
                productBatchRepository.findByPurchaseIdAndProduct_ProductId(request.getPurchaseId(), request.getProductId())
        ).orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PRODUCT_BATCH_NOT_FOUND));

        // Fetch Batch and validate
        Batch batch = batchRepository.findById(productBatch.getBatch().getBatchId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.BATCH_NOT_FOUND)
        );

        //add return details
        List<PurchaseReturn> purchaseReturns = purchaseReturnRepository.findByPurchase_PurchaseId(productBatch.getPurchaseId());

        // Deduct returned amounts
        double totalReturnedAmount = purchaseReturns.stream()
                .mapToDouble(PurchaseReturn::getRefundAmount)
                .sum();


        // Adjust the quantity for each PurchaseProduct
        double quantityReturned = purchaseReturns.stream()
                .mapToDouble(PurchaseReturn::getQuantityReturned)
                .sum();

        ProductBatchResponse response = new ProductBatchResponse();
        response.setPurchaseId(productBatch.getPurchaseId());
        response.setBatchNumber(batch.getBatchNumber());
        response.setProductId(productBatch.getProduct().getProductId());
        response.setProductName(productBatch.getProduct().getProductName());
        response.setQty(productBatch.getQty());
        response.setUnitCost(productBatch.getUnitCost());
        response.setRetailPrice(productBatch.getRetailPrice());
        response.setRefundedAmount(totalReturnedAmount);
        response.setRefundedQty(quantityReturned);
        return response;
    }

    @Override
    public PurchaseResponse getPurchaseByIdentifier(String identifier) {
        Purchase purchase;
        if (identifier.matches("\\d+")) { // Check if the identifier is numeric
            Integer purchaseId = Integer.parseInt(identifier);
            purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PURCHASE_NOT_FOUND));
        } else {
            purchase = purchaseRepository.findByInvoiceNumber(identifier)
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PURCHASE_NOT_FOUND));
        }
        PurchaseResponse purchaseResponse = purchaseMapper.toPurchaseResponse(purchase);
        purchaseResponse.setSupplierName(
                supplierRepository.findById(purchase.getSupplierId())
                        .map(Supplier::getName)
                        .orElse(null)
        );

        return purchaseResponse;
    }

    @Override
    @Transactional
    public PurchaseReturnResponse processPurchaseReturn(PurchaseReturnRequest purchaseReturnRequest) {
        // Fetch the purchase by ID
        Purchase purchase = purchaseRepository.findById(purchaseReturnRequest.getPurchaseId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PURCHASE_NOT_FOUND));

        // Process each return item
        purchaseReturnRequest.getReturns().forEach(returnItem -> {
            // Validate if the product is part of the purchase
            ProductBatch productBatch = productBatchRepository.findByPurchaseIdAndProduct_ProductId(
                            purchase.getPurchaseId(), returnItem.getProductId());
            if (productBatch == null) {
                throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND);
            }

            // Validate the return quantity
            if (returnItem.getQuantityToReturn() > productBatch.getQty()) {
                throw new IllegalArgumentException("Return quantity exceeds purchased quantity");
            }

            // Fetch the batch by product and batch number
            Batch batch = batchRepository.findByBatchNumberAndSku(productBatch.getBatch().getBatchNumber(), productBatch.getProduct().getSku())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.BATCH_NOT_FOUND));

            // Update inventory quantity
            batch.setQty(batch.getQty() - returnItem.getQuantityToReturn());

            batchRepository.save(batch);

            // Save return details
            PurchaseReturn purchaseReturn = new PurchaseReturn();
            purchaseReturn.setPurchase(purchase);
            purchaseReturn.setSupplierName(purchaseReturnRequest.getSupplierName());
            purchaseReturn.setSupplierId(purchase.getSupplierId());
            purchaseReturn.setInvoiceNumber(purchaseReturnRequest.getInvoiceNumber());
            purchaseReturn.setProduct(productBatch.getProduct());
            purchaseReturn.setQuantityReturned(returnItem.getQuantityToReturn());
            purchaseReturn.setCondition(returnItem.getCondition());
            purchaseReturn.setReason(returnItem.getReason());
            purchaseReturn.setRefundAmount(returnItem.getRefundAmount());
            purchaseReturn.setReturnDate(new Date());
            purchaseReturn.setBatchNumber(returnItem.getBatchNumber());
            purchaseReturnRepository.save(purchaseReturn);
        });

        PurchaseReturnResponse purchaseReturnResponse = new PurchaseReturnResponse();
        purchaseReturnResponse.setInvoiceNumber("9797");
        return purchaseReturnResponse;
    }

    @Override
    public List<PurchaseReturnResponse> getAllPurchaseReturns() {
        return purchaseReturnRepository.findAll().stream().map(purchaseReturn -> {
            PurchaseReturnResponse response = purchaseReturnMapper.toPurchaseReturnResponse(purchaseReturn);
            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            response.setReturnDate(sm.format(purchaseReturn.getReturnDate()));
            return response;
        }).toList();
    }

    @Override
    public List<PaymentDetails> getPaymentDetailsByPurchaseId(Integer purchaseId) {
        // Fetch the payment by invoice number
        Payment payment = paymentRepository.findByReferenceIdAndPaymentPaymentTypeAndPurchaseReferenceType(purchaseId.toString())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PAYMENT_NOT_FOUND));

        // Fetch and return the associated payment details
        return paymentDetailsRepository.findByPayment(payment);
    }

    @Override
    @Transactional
    public PaymentDetailsResponse createPaymentDetails(PaymentDetailsRequest paymentDetailsRequest) {
        // Fetch the associated Payment entity
        Payment payment = paymentRepository.findById(paymentDetailsRequest.getPaymentId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PAYMENT_NOT_FOUND));

        // Create and populate the PaymentDetails entity
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPayment(payment);
        paymentDetails.setPaymentMethod(paymentDetailsRequest.getMethod());
        paymentDetails.setAmount(paymentDetailsRequest.getAmount());
        paymentDetails.setChequeNo(paymentDetailsRequest.getChequeNo());
        paymentDetails.setBankName(paymentDetailsRequest.getBankName());
        paymentDetails.setChequeDate(paymentDetailsRequest.getChequeDate());
        paymentDetails.setPaymentDate(new Date());
        if (paymentDetailsRequest.getMethod().equalsIgnoreCase("CHEQUE")) {
            paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            paymentDetails.setPaymentStatus(PaymentStatus.CLEARED);
        }

        // Save the PaymentDetails entity
        return paymentDetailsMapper.toPaymentDetailsResponse(paymentDetailsRepository.save(paymentDetails));
    }
}
