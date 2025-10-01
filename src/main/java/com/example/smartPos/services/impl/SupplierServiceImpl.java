package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SupplierRequest;
import com.example.smartPos.controllers.requests.TransactionDetails;
import com.example.smartPos.controllers.responses.SupplierOutstandingResponse;
import com.example.smartPos.controllers.responses.SupplierPaymentDetailsResponse;
import com.example.smartPos.controllers.responses.SupplierResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.PaymentDetailsRepository;
import com.example.smartPos.repositories.PurchaseRepository;
import com.example.smartPos.repositories.PurchaseReturnRepository;
import com.example.smartPos.repositories.SupplierRepository;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.ISupplierService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.SupplierConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements ISupplierService {

    private final SupplierRepository supplierRepository;

    private final PurchaseRepository purchaseRepository;

    private final PurchaseReturnRepository purchaseReturnRepository;

    private final PaymentDetailsRepository paymentDetailsRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository, PurchaseRepository purchaseRepository, PurchaseReturnRepository purchaseReturnRepository, PaymentDetailsRepository paymentDetailsRepository) {
        this.supplierRepository = supplierRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }


    @Override
    public List<SupplierResponse> getAllSuppliers() {

        List<Supplier> supplierList = supplierRepository.findAllByStatus(1);
        return supplierList.stream().map(supplier -> {
            SupplierResponse supResp = new SupplierResponse();
            supResp.setSupId(supplier.getSupId());
            supResp.setPhone(supplier.getPhone());
            supResp.setName(supplier.getName());
            supResp.setAddress(supplier.getAddress());
            supResp.setEmail(supplier.getEmail());
            supResp.setStatus(supplier.getStatus());
            return supResp;
        }).toList();

    }

    @Override
    public List<SupplierResponse> getAllSuppliersWithoutStatus() {

        List<Supplier> supplierList = supplierRepository.findAll();
        return supplierList.stream().map(supplier -> {
            SupplierResponse supResp = new SupplierResponse();
            supResp.setSupId(supplier.getSupId());
            supResp.setPhone(supplier.getPhone());
            supResp.setName(supplier.getName());
            supResp.setAddress(supplier.getAddress());
            supResp.setEmail(supplier.getEmail());
            supResp.setStatus(supplier.getStatus());
            return supResp;
        }).toList();

    }

    @Override
    public SupplierResponse getSupplierByPhone(String mobileNo) {
        Supplier supplier = supplierRepository.findByPhone(mobileNo).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
        );
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setSupId(supplier.getSupId());
        supplierResponse.setPhone(supplier.getPhone());
        supplierResponse.setName(supplier.getName());
        supplierResponse.setAddress(supplier.getAddress());
        supplierResponse.setEmail(supplier.getEmail());
        return supplierResponse;
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
        );
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setSupId(supplier.getSupId());
        supplierResponse.setPhone(supplier.getPhone());
        supplierResponse.setName(supplier.getName());
        supplierResponse.setAddress(supplier.getAddress());
        supplierResponse.setEmail(supplier.getEmail());
        return supplierResponse;
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest supplierRequest) {
        Optional<Supplier> supplier = supplierRepository.findByPhone(supplierRequest.getPhone());
        if (supplier.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_SUPPLIER);
        }
        // Retrieve the currently authenticated user's username
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Supplier saveSupplier = new Supplier();
        saveSupplier.setName(supplierRequest.getName());
        saveSupplier.setAddress(supplierRequest.getAddress());
        saveSupplier.setPhone(supplierRequest.getPhone());
        saveSupplier.setEmail(supplierRequest.getEmail());
        saveSupplier.setStatus(1);
        saveSupplier.fillNew(currentUser);
        Supplier savedSupplier = supplierRepository.save(saveSupplier);

        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setPhone(savedSupplier.getPhone());
        supplierResponse.setStatusCode(SupplierConstants.STATUS_201);
        supplierResponse.setDesc(SupplierConstants.MESSAGE_201);
        return supplierResponse;
    }

    @Override
    public SupplierResponse updateSupplier(SupplierRequest supplierRequest) {
        SupplierResponse updatedSupplierResponse = new SupplierResponse();
        if (supplierRequest.getSupId() != null) {
            Supplier supplier = supplierRepository.findById(supplierRequest.getSupId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
            );
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            supplier.setPhone(supplierRequest.getPhone());
            supplier.setName(supplierRequest.getName());
            supplier.setAddress(supplierRequest.getAddress());
            supplier.setEmail(supplierRequest.getEmail());
            supplier.fillUpdated(currentUser);
            Supplier updatedSupplier = supplierRepository.save(supplier);

            updatedSupplierResponse.setSupId(updatedSupplier.getSupId());
            updatedSupplierResponse.setPhone(updatedSupplier.getPhone());
            updatedSupplierResponse.setName(updatedSupplier.getName());
            updatedSupplierResponse.setAddress(updatedSupplier.getAddress());
            updatedSupplierResponse.setEmail(updatedSupplier.getEmail());
        }
        return updatedSupplierResponse;
    }

    @Override
    public void deletSupplier(Integer supId) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supId);
        if (supplierOptional.isPresent()){
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            Supplier supplier = supplierOptional.get();
            supplier.setStatus(0);
            supplier.fillUpdated(currentUser);
            supplierRepository.save(supplier);
        }
    }

    @Override
    public List<SupplierResponse> getAllSupplierDetailsWithSummary() {
        List<Supplier> suppliers = supplierRepository.findAll();

        // Step 2: Fetch all sales with suppliers
        List<Purchase> allPurchases = purchaseRepository.findAllWithSupplier();
        Map<Integer, List<Purchase>> purchaseBySupplier = allPurchases.stream()
                .collect(Collectors.groupingBy(Purchase::getSupplierId));

        // Step 3: Fetch all returns
        List<PurchaseReturn> allReturns = purchaseReturnRepository.findAllWithSupplier();
        Map<Integer, Double> returnsBySupplier = allReturns.stream()
                .collect(Collectors.groupingBy(PurchaseReturn::getSupplierId,
                        Collectors.summingDouble(PurchaseReturn::getRefundAmount)));

        // Step 4: Fetch all payments with invoices
        List<PaymentDetails> allPayments = paymentDetailsRepository.findAllByPaymentPaymentTypeAndPurchaseReferenceType();
        Map<String, Double> paymentsByPurchaseId = allPayments.stream()
                .filter(pd -> pd.getPayment() != null && pd.getPayment().getReferenceId() != null)
                .collect(Collectors.groupingBy(pd -> pd.getPayment().getReferenceId(),
                        Collectors.summingDouble(PaymentDetails::getAmount)));

        // Step 5: Map customers to response
        return suppliers.stream().map(supplier -> {
            List<Purchase> supplierPurchases = purchaseBySupplier.getOrDefault(supplier.getSupId(), Collections.emptyList());

            double totalPurchases = supplierPurchases.stream()
                    .mapToDouble(Purchase::getTotalCost)
                    .sum();

            double totalReturns = returnsBySupplier.getOrDefault(supplier.getSupId(), 0.0);

            double totalPaid = supplierPurchases.stream()
                    .mapToDouble(purchase -> paymentsByPurchaseId.getOrDefault(purchase.getPurchaseId().toString(), 0.0))
                    .sum();

            double outstanding = totalPurchases - totalReturns - totalPaid;

            SupplierResponse response = new SupplierResponse();
            response.setSupId(supplier.getSupId());
            response.setName(supplier.getName());
            response.setPhone(supplier.getPhone());
            response.setAddress(supplier.getAddress());
            response.setEmail(supplier.getEmail());
            response.setTotalPurchases(totalPurchases);
            response.setTotalReturns(totalReturns);
            response.setTotalPayments(totalPaid);
            response.setTotalOutstanding(outstanding);

            return response;
        }).toList();
    }

    @Override
    public SupplierPaymentDetailsResponse getSupplierDetailsWithSummary(Integer supId) {
        // Fetch customer details
        Supplier supplier = supplierRepository.findById(supId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
        );

        // Fetch sales, returns, and payment details
        List<Purchase> purchases = purchaseRepository.findAllPurchasesWithSupplier(supplier.getSupId());
        List<PurchaseReturn> purchaseReturns = purchaseReturnRepository.findBySupplierId(supId);
        List<PaymentDetails> paymentDetails = paymentDetailsRepository.findAllByPaymentPaymentTypeAndPurchaseReferenceType();

        // Group payments by invoice number
        Map<String, Double> paymentsByPurchaseId = paymentDetails.stream()
                .filter(pd -> pd.getPayment() != null && pd.getPayment().getReferenceId() != null)
                .collect(Collectors.groupingBy(pd -> pd.getPayment().getReferenceId(),
                        Collectors.summingDouble(PaymentDetails::getAmount)));

        // Calculate totals
        double totalPurchases = purchases.stream().mapToDouble(Purchase::getTotalCost).sum();
        double totalReturns = purchaseReturns.stream().mapToDouble(PurchaseReturn::getRefundAmount).sum();
        double totalPaidAmount = purchases.stream()
                .mapToDouble(purchase -> paymentsByPurchaseId.getOrDefault(purchase.getPurchaseId().toString(), 0.0))
                .sum();
        double netSales = totalPurchases - totalReturns;
        double totalOutstanding = netSales - totalPaidAmount;

        // Prepare transaction details
        List<TransactionDetails> transactionDetails = prepareTransactionDetails(paymentDetails, purchaseReturns, supId);

        // Prepare response
        return buildSupplierResponse(supplier, totalPurchases, totalReturns, totalPaidAmount, totalOutstanding, transactionDetails);
    }


    private List<TransactionDetails> prepareTransactionDetails(List<PaymentDetails> paymentDetails, List<PurchaseReturn> purchaseReturns, Integer supplierId) {
        // Filter payment details for the specific supplier
        List<PaymentDetails> supplierPayments = paymentDetails.stream()
                .filter(payment -> payment.getPayment().getSupplier().getSupId().equals(supplierId))
                .toList();

        // Filter sales returns for the specific customer
        List<PurchaseReturn> supplierPurchasesReturns = purchaseReturns.stream()
                .filter(purchaseReturn -> purchaseReturn.getSupplierId().equals(supplierId))
                .toList();

        // Map sale transactions
        List<TransactionDetails> purchaseTransactions = supplierPayments.stream()
                .collect(Collectors.groupingBy(payment -> payment.getPayment().getReferenceId()))
                .entrySet()
                .stream()
                .map(entry -> {
                    String referenceId = entry.getKey();
                    List<PaymentDetails> payments = entry.getValue();

                    // Calculate total sale amount and cumulative payments
                    double totalPurchaseAmount = payments.get(0).getPayment().getTotalAmount();
                    double cumulativePayments = payments.stream().mapToDouble(PaymentDetails::getAmount).sum();

                    TransactionDetails details = new TransactionDetails();
                    details.setDate(payments.get(0).getPayment().getPaymentDate());
                    details.setType("Purchase");
                    details.setInvoiceNo(payments.get(0).getPayment().getRemarks());
                    details.setDebit(totalPurchaseAmount);
                    details.setCredit(0);
                    details.setBalance(totalPurchaseAmount - cumulativePayments);
                    return details;
                }).toList();

        // Map payment transactions
        List<TransactionDetails> paymentTransactions = supplierPayments.stream().map(payment -> {
            TransactionDetails details = new TransactionDetails();
            details.setDate(payment.getPaymentDate());
            details.setType("Payment");
            details.setInvoiceNo(payment.getPayment().getRemarks());
            details.setDebit(0);
            details.setCredit(payment.getAmount());
            details.setBalance(0); // Temporary, will calculate later
            details.setPaymentMethod(payment.getPaymentMethod());
            details.setChequeNo(payment.getChequeNo());
            details.setChequeDate(payment.getChequeDate());
            details.setPaymentStatus(payment.getPaymentStatus().name());
            return details;
        }).collect(Collectors.toList());

        // Map return transactions
        List<TransactionDetails> returnTransactions = supplierPurchasesReturns.stream().map(purchaseReturn -> {
            TransactionDetails details = new TransactionDetails();
            details.setDate(purchaseReturn.getReturnDate());
            details.setType("Return");
            details.setInvoiceNo(purchaseReturn.getInvoiceNumber());
            details.setDebit(0);
            details.setCredit(purchaseReturn.getRefundAmount());
            details.setBalance(0); // Temporary, will calculate later
            return details;
        }).toList();

        // Combine all transactions
        paymentTransactions.addAll(returnTransactions);
        paymentTransactions.addAll(purchaseTransactions);

//        // Sort by date and type
        List<TransactionDetails> sortedTransactions = paymentTransactions.stream()
                .sorted(Comparator.comparing(TransactionDetails::getDate)) // Secondary sort by type
                .collect(Collectors.toList());

        // Calculate running balance
        double runningBalance = 0.0;
        for (TransactionDetails transaction : sortedTransactions) {
            runningBalance += transaction.getCredit() - transaction.getDebit();
            transaction.setBalance(runningBalance);
        }

        return sortedTransactions;
    }

    // Helper method to build the customer response
    private SupplierPaymentDetailsResponse buildSupplierResponse(Supplier supplier, double totalSales, double totalReturns, double totalPaidAmount, double totalOutstanding, List<TransactionDetails> transactionDetails) {
        SupplierPaymentDetailsResponse response = new SupplierPaymentDetailsResponse();
        response.setSupplierId(supplier.getSupId());
        response.setSupplierName(supplier.getName());
        response.setTotalPurchases(totalSales);
        response.setTotalReturns(totalReturns);
        response.setOutstanding(totalOutstanding);
        response.setTotalPayments(totalPaidAmount);
        response.setTransactionDetails(transactionDetails);
        return response;
    }

    @Override
    public List<SupplierOutstandingResponse> getSupplierOutstanding(Integer supplierId) {
        // Fetch all purchases for the supplier
        List<Purchase> purchases = purchaseRepository.findAllPurchasesWithSupplier(supplierId);

        // Fetch all payment details for the supplier's purchases in a single query
        List<PaymentDetails> paymentDetails = paymentDetailsRepository.findByInvoiceNumbersAndPaymentPaymentTypeAndPurchasePaymentType(
                purchases.stream().map(purchase -> purchase.getPurchaseId().toString()).toList()
        );

        // Group payment details by purchaseId
        Map<String, Double> paymentsByPurchaseId = paymentDetails.stream()
                .collect(Collectors.groupingBy(
                        pd -> pd.getPayment().getReferenceId(),
                        Collectors.summingDouble(PaymentDetails::getAmount)
                ));

        // Map purchases to responses
        return purchases.stream().map(purchase -> {
            double paidAmount = paymentsByPurchaseId.getOrDefault(purchase.getPurchaseId().toString(), 0.0);
            double outstanding = purchase.getTotalCost() - paidAmount;

            SupplierOutstandingResponse response = new SupplierOutstandingResponse();
            response.setInvoiceNumber(purchase.getInvoiceNumber());
            response.setPurchaseDate(purchase.getInvoiceDate());
            response.setTotalAmount(purchase.getTotalCost());
            response.setPaidAmount(paidAmount);
            response.setOutstanding(outstanding);
            return response;
        }).collect(Collectors.toList());
    }
}
