package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.*;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.mapper.CustomerMapper;
import com.example.smartPos.mapper.PaymentDetailsMapper;
import com.example.smartPos.mapper.SalesReturnMapper;
import com.example.smartPos.mapper.UserMapper;
import com.example.smartPos.repositories.*;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.ISaleService;
import com.example.smartPos.services.ISmsService;
import com.example.smartPos.util.*;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
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

    private final SalesReturnRepository salesReturnRepository;

    private final BatchRepository batchRepository;

    private final CustomerMapper customerMapper;

    private final UserMapper userMapper;

    private final SalesReturnMapper salesReturnMapper;

    private final PaymentRepository paymentRepository;

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final PaymentDetailsMapper paymentDetailsMapper;

    private final ISmsService smsService;

    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;

    public SaleServiceImpl(SaleRepository saleRepository, ProductRepository productRepository, SalesReturnRepository salesReturnRepository, BatchRepository batchRepository, CustomerMapper customerMapper, UserMapper userMapper, SalesReturnMapper salesReturnMapper, PaymentRepository paymentRepository, PaymentDetailsRepository paymentDetailsRepository, PaymentDetailsMapper paymentDetailsMapper, ISmsService smsService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.salesReturnRepository = salesReturnRepository;
        this.batchRepository = batchRepository;
        this.customerMapper = customerMapper;
        this.userMapper = userMapper;
        this.salesReturnMapper = salesReturnMapper;
        this.paymentRepository = paymentRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.paymentDetailsMapper = paymentDetailsMapper;
        this.smsService = smsService;
    }

    @Override
    public List<SaleResponse> getAllSales() {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Fetch all sales with status 1
        List<Sale> sales = saleRepository.findAllByStatus(1);

        // Fetch all payment details for the sales in a single query
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findByInvoiceNumbersAndReceiptPaymentTypeAndSalePaymentType(
                sales.stream().map(Sale::getInvoiceNumber).toList()
        );

        // Group payment details by sale ID
        Map<String, Double> totalPaidAmountByInvoiceNum = paymentDetailsList.stream()
                .collect(Collectors.groupingBy(
                        pd -> pd.getPayment().getReferenceId(),
                        Collectors.summingDouble(PaymentDetails::getAmount)
                ));

        // Map sales to SaleResponse
        return sales.stream().map(sale -> {
            Double totalPaidAmount = totalPaidAmountByInvoiceNum.getOrDefault(sale.getInvoiceNumber(), 0.0);
            Double outstandingBalance = sale.getTotalAmount() - totalPaidAmount;

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
            saleResponse.setPaidAmount(totalPaidAmount);
            saleResponse.setOutstandingBalance(outstandingBalance);
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

        //add return details
        List<SalesReturn> salesReturns = salesReturnRepository.findBySale_SaleId(sale.getSaleId());

        // Deduct returned amounts
        double totalReturnedAmount = salesReturns.stream()
                .mapToDouble(SalesReturn::getRefundAmount)
                .sum();


        // Adjust the quantity for each SaleProduct
        double quantityReturned = salesReturns.stream()
                .mapToDouble(SalesReturn::getQuantityReturned)
                .sum();

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
            response.setRefundedQty(quantityReturned);
            response.setRefundedAmount(totalReturnedAmount);
            response.setProductDeliveryStatus(soldProduct.getStatus());
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

            // Process and save payment details
            Payment payment = createPaymentEntity(saleRequest, sale, currentUser);
            paymentRepository.save(payment);

            // Save payment details
            PaymentDetails paymentDetailsList = createPaymentDetailsEntities(saleRequest, payment, currentUser);
            paymentDetailsRepository.save(paymentDetailsList);

            Sale savedSale = saleRepository.save(sale);

            // Map the saved Sale to SaleResponse
            return mapToSaleResponse(savedSale);
        } catch (Exception e) {
            throw new ServiceException("Saving Unsuccessful", e);
        }
    }

    private Payment createPaymentEntity(SaleRequest saleRequest, Sale sale, String currentUser) {
        Payment payment = new Payment();
        payment.setPaymentType(PaymentType.RECEIPT);
        payment.setReferenceId(sale.getInvoiceNumber());
        payment.setReferenceType(ReferenceType.SALE);
        payment.setCustomer(sale.getCustomer());
        payment.setPaymentDate(new Date());
        payment.setTotalAmount(saleRequest.getTotalAmount());
        payment.setRemarks("Payment for INV ID: " + sale.getInvoiceNumber());
        payment.fillNew(currentUser);
        return payment;
    }

    private PaymentDetails createPaymentDetailsEntities(SaleRequest saleRequest, Payment payment, String currentUser) {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setPayment(payment);
        paymentDetails.setPaymentMethod(saleRequest.getPaymentType());
        paymentDetails.setAmount(saleRequest.getPaidAmount());
        paymentDetails.setChequeNo(saleRequest.getChequeNumber());
        paymentDetails.setBankName(saleRequest.getBankName());
        paymentDetails.setChequeDate(saleRequest.getChequeDate());
        paymentDetails.setPaymentDate(new Date());
        if (saleRequest.getPaymentType().equalsIgnoreCase("CHEQUE")) {
            paymentDetails.setPaymentStatus(PaymentStatus.PENDING);
        } else {
            paymentDetails.setPaymentStatus(PaymentStatus.CLEARED);
        }
        paymentDetails.fillNew(currentUser);

        return paymentDetails;
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
        saleProduct.setStatus("PENDING");
        return saleProduct;
    }

    private SaleResponse mapToSaleResponse(Sale savedSale) {
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
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
        saleResponse.setPaidAmount(savedSale.getPaidAmount());
        saleResponse.setFullyPaid(savedSale.getFullyPaid());
        saleResponse.setPaymentMethod(savedSale.getPaymentMethod());
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


    @Override
    public SaleResponse getSaleByInvoiceNumber(String invoiceNumber) {
        // Fetch the sale by invoice number
        Sale sale = saleRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND));

        // Map the adjusted sale to SaleResponse
        SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setSaleId(sale.getSaleId());
        saleResponse.setCustomer(sale.getCustomer());
        saleResponse.setSaleDate(sm.format(sale.getSaleDate()));
        saleResponse.setSubTotal(sale.getSubTotal());
        saleResponse.setPaymentMethod(sale.getPaymentMethod());
        saleResponse.setTotalAmount(sale.getTotalAmount());
        saleResponse.setInvoiceNumber(sale.getInvoiceNumber());
        saleResponse.setSoldProducts(sale.getSaleProducts().stream().map(saleProduct -> {
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
        }).toList());
        saleResponse.setPaidAmount(sale.getPaidAmount());
        saleResponse.setFullyPaid(sale.getFullyPaid());
        saleResponse.setPaymentMethod(sale.getPaymentMethod());
        saleResponse.setStatusCode(SaleConstants.STATUS_201);
        saleResponse.setDesc(SaleConstants.MESSAGE_201);
        return saleResponse;
    }

    @Override
    @Transactional
    public void processSalesReturn(SalesReturnRequest salesReturnRequest) {
        // Fetch the sale by ID
        Sale sale = saleRepository.findById(salesReturnRequest.getSaleId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND));

        // Process each return item
        salesReturnRequest.getReturns().forEach(returnItem -> {
            // Validate if the product is part of the sale
            SaleProduct saleProduct = sale.getSaleProducts().stream()
                    .filter(sp -> sp.getProduct().getProductId().equals(returnItem.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND));

            // Validate the return quantity
            if (returnItem.getQuantityToReturn() > saleProduct.getQuantity()) {
                throw new IllegalArgumentException("Return quantity exceeds sold quantity");
            }

            // Fetch the batch by product and batch number
            Batch batch = batchRepository.findByBatchNumberAndSku(saleProduct.getBatchNo(), saleProduct.getProduct().getSku())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.BATCH_NOT_FOUND));

            // Update inventory quantity
            if (returnItem.getCondition().equals("Good")) {
                batch.setQty(batch.getQty() + returnItem.getQuantityToReturn());
            }
            batchRepository.save(batch);

            // Save return details
            SalesReturn salesReturn = new SalesReturn();
            salesReturn.setSale(sale);
            salesReturn.setCustomerName(salesReturnRequest.getCustomerName());
            salesReturn.setCustomerId(salesReturnRequest.getCustomerId());
            salesReturn.setInvoiceNumber(salesReturnRequest.getInvoiceNumber());
            salesReturn.setProduct(saleProduct.getProduct());
            salesReturn.setQuantityReturned(returnItem.getQuantityToReturn());
            salesReturn.setCondition(returnItem.getCondition());
            salesReturn.setReason(returnItem.getReason());
            salesReturn.setRefundAmount(returnItem.getRefundAmount());
            salesReturn.setReturnDate(new Date());
            salesReturn.setBatchNumber(returnItem.getBatchNumber());
            salesReturnRepository.save(salesReturn);
        });
    }

    @Override
    public List<SalesReturnResponse> getAllSalesReturns() {
        return salesReturnRepository.findAll().stream().map(salesReturn -> {
            SalesReturnResponse response = salesReturnMapper.toSalesReturnResponse(salesReturn);
            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            response.setReturnDate(sm.format(salesReturn.getReturnDate()));
            return response;
        }).toList();
    }

    @Override
    public List<PaymentDetails> getPaymentDetailsByInvoice(String invoiceNumber) {
        // Fetch the payment by invoice number
        Payment payment = paymentRepository.findByReferenceIdAndReceiptPaymentTypeAndSaleReferenceType(invoiceNumber)
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


    @Override
    public void sendSaleDetailsSms(Integer saleId) {
        // Fetch sale details from the database
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND));

        // Generate a link for the customer to view the sale details
        String baseUrl = allowedOrigin + "/#/public/sale/"; // Replace with your actual domain
        String saleDetailsLink = baseUrl + saleId;

        // Build SMS content
        String smsContent = "Thank you for your purchase! View your sale details here: " + saleDetailsLink;

        // Send SMS using an SMS service (e.g., Twilio)
        String customerPhoneNumber = sale.getCustomer().getPhone();
        smsService.sendSms(customerPhoneNumber, smsContent);
    }

    @Override
    public List<CustomerViewSaleResponse> fetchSaleProductsOfCustomerView(Integer saleId) {
        Sale sale = saleRepository.findSaleWithProductsById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND));

        // Map SaleProducts to CustomerViewSaleResponse
        return sale.getSaleProducts().stream().map(soldProduct -> {
            CustomerViewSaleResponse response = new CustomerViewSaleResponse();
            response.setProduct(soldProduct.getProduct().getProductName());
            response.setQty(soldProduct.getQuantity());
            response.setStatus(soldProduct.getStatus());
            response.setProductId(soldProduct.getProduct().getProductId());
            response.setBatchNo(soldProduct.getBatchNo());
            return response;
        }).toList();
    }

    @Override
    public List<ProductStatusUpdateResponse> updateProductStatus(ProductStatusUpdateRequest request) {
        Sale sale = saleRepository.findSaleWithProductsById(request.getSaleId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.SALE_NOT_FOUND));

        // Find the matching SaleProduct
        SaleProduct saleProduct = sale.getSaleProducts().stream()
                .filter(sp -> sp.getProduct().getProductId().equals(request.getProductId()) && sp.getBatchNo().equals(request.getBatchNo()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND));

        // Update the status
        saleProduct.setStatus(request.getStatus());

        // Save the updated SaleProduct
        Sale savedSale = saleRepository.save(sale);

        return savedSale.getSaleProducts().stream().map(soldProduct -> {
            ProductStatusUpdateResponse response = new ProductStatusUpdateResponse();
            response.setSaleId(soldProduct.getSale().getSaleId());
            response.setProductId(soldProduct.getProduct().getProductId());
            response.setBatchNo(soldProduct.getBatchNo());
            response.setStatus(soldProduct.getStatus());
            return response;
        }).toList();
    }
}
