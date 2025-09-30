package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.requests.TransactionDetails;
import com.example.smartPos.controllers.responses.CustomerPaymentDetailsResponse;
import com.example.smartPos.controllers.responses.CustomerResponse;
import com.example.smartPos.controllers.responses.PaymentDetailsResponse;
import com.example.smartPos.controllers.responses.PaymentResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.CustomerRepository;
import com.example.smartPos.repositories.PaymentDetailsRepository;
import com.example.smartPos.repositories.SaleRepository;
import com.example.smartPos.repositories.SalesReturnRepository;
import com.example.smartPos.repositories.model.*;
import com.example.smartPos.services.ICustomerService;
import com.example.smartPos.util.CustomerConstants;
import com.example.smartPos.util.ErrorCodes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements ICustomerService {

    private final CustomerRepository customerRepository;

    private final SaleRepository saleRepository;

    private final SalesReturnRepository salesReturnRepository;

    private final PaymentDetailsRepository paymentDetailsRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, SaleRepository saleRepository, SalesReturnRepository salesReturnRepository, PaymentDetailsRepository paymentDetailsRepository) {
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
        this.salesReturnRepository = salesReturnRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }


    @Override
    public List<CustomerResponse> getAllCustomers() {

        List<Customer> customerList = customerRepository.findAllByStatus(1);
        return customerList.stream().map(customer -> {
            CustomerResponse custResp = new CustomerResponse();
            custResp.setCustId(customer.getCustId());
            custResp.setPhone(customer.getPhone());
            custResp.setName(customer.getName());
            custResp.setAddress(customer.getAddress());
            custResp.setEmail(customer.getEmail());
            custResp.setStatus(customer.getStatus());
            return custResp;
        }).toList();

    }

    @Override
    public List<CustomerResponse> getAllCustomersWithoutStatus() {

        List<Customer> customerList = customerRepository.findAll();
        return customerList.stream().map(customer -> {
            CustomerResponse custResp = new CustomerResponse();
            custResp.setCustId(customer.getCustId());
            custResp.setPhone(customer.getPhone());
            custResp.setName(customer.getName());
            custResp.setAddress(customer.getAddress());
            custResp.setEmail(customer.getEmail());
            custResp.setStatus(customer.getStatus());
            return custResp;
        }).toList();

    }

    @Override
    public CustomerResponse getCustomerByPhone(String mobileNo) {
        Customer customer = customerRepository.findByPhone(mobileNo).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
        );
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustId(customer.getCustId());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setName(customer.getName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }

    @Override
    public CustomerResponse getCustomerById(Integer custId) {
        Customer customer = customerRepository.findById(custId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
        );
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustId(customer.getCustId());
        customerResponse.setPhone(customer.getPhone());
        customerResponse.setName(customer.getName());
        customerResponse.setAddress(customer.getAddress());
        customerResponse.setEmail(customer.getEmail());
        return customerResponse;
    }

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Optional<Customer> customer = customerRepository.findByPhone(customerRequest.getPhone());
        if (customer.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_CUSTOMER);
        }

        // Retrieve the currently authenticated user's username
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer saveCustomer = new Customer();
        saveCustomer.setName(customerRequest.getName());
        saveCustomer.setAddress(customerRequest.getAddress());
        saveCustomer.setPhone(customerRequest.getPhone());
        saveCustomer.setEmail(customerRequest.getEmail());
        saveCustomer.setStatus(1);
        saveCustomer.fillNew(currentUser);
        Customer savedCustomer = customerRepository.save(saveCustomer);

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setPhone(savedCustomer.getPhone());
        customerResponse.setStatusCode(CustomerConstants.STATUS_201);
        customerResponse.setDesc(CustomerConstants.MESSAGE_201);
        return customerResponse;
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
        CustomerResponse updatedCustomerResponse = new CustomerResponse();
        if (customerRequest.getCustId() != null) {
            Customer customer = customerRepository.findById(customerRequest.getCustId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
            );
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            customer.setPhone(customerRequest.getPhone());
            customer.setName(customerRequest.getName());
            customer.setAddress(customerRequest.getAddress());
            customer.setEmail(customerRequest.getEmail());
            customer.fillUpdated(currentUser);
            Customer updatedCustomer = customerRepository.save(customer);

            updatedCustomerResponse.setCustId(updatedCustomer.getCustId());
            updatedCustomerResponse.setPhone(updatedCustomer.getPhone());
            updatedCustomerResponse.setName(updatedCustomer.getName());
            updatedCustomerResponse.setAddress(updatedCustomer.getAddress());
            updatedCustomerResponse.setEmail(updatedCustomer.getEmail());
        }
        return updatedCustomerResponse;
    }

    public List<CustomerResponse> getCustomersByPhoneNumber(String phoneNumber) {
        List<Customer> customers = customerRepository.findByPhoneContaining(phoneNumber);
        List<CustomerResponse> customerDtos = customers.stream()
                .map(customer -> {
                    CustomerResponse customerResponse = new CustomerResponse();
                    customerResponse.setCustId(customer.getCustId());
                    customerResponse.setPhone(customer.getPhone());
                    customerResponse.setName(customer.getName());
                    return customerResponse;
                }).toList();

        return customerDtos;
    }

    @Override
    public void deletCustomer(Integer custId) {
        Optional<Customer> customerOptional = customerRepository.findById(custId);
        if (customerOptional.isPresent()){
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            Customer customer = customerOptional.get();
            customer.setStatus(0);
            customer.fillUpdated(currentUser);
            customerRepository.save(customer);
        }
    }

    @Override
    public List<CustomerResponse> getCustomersByName(String name) {
        List<Customer> customers = customerRepository.findByNameContaining(name);
        return customers.stream().map(customer -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setCustId(customer.getCustId());
            customerResponse.setName(customer.getName());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setAddress(customer.getAddress());
            customerResponse.setEmail(customer.getEmail());
            return customerResponse;
        }).toList();
    }

    @Override
    public List<CustomerResponse> getCustomersByNameOrPhone(String nameOrPhone) {
        List<Customer> customers;
        if (nameOrPhone != null) {
            customers = customerRepository.findByNameOrPhone(nameOrPhone);
        } else {
            customers = List.of();
        }

        return customers.stream().map(customer -> {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setCustId(customer.getCustId());
            customerResponse.setName(customer.getName());
            customerResponse.setPhone(customer.getPhone());
            customerResponse.setAddress(customer.getAddress());
            customerResponse.setEmail(customer.getEmail());
            return customerResponse;
        }).toList();
    }

    @Override
    public CustomerPaymentDetailsResponse getCustomerDetailsWithSummary(Integer custId) {
        // Fetch customer details
        Customer customer = customerRepository.findById(custId).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CUSTOMER_NOT_FOUND)
        );

        // Fetch sales, returns, and payment details
        List<Sale> sales = saleRepository.findAllSalesWithCustomer(customer.getCustId());
        List<SalesReturn> salesReturns = salesReturnRepository.findByCustomerId(custId);
        List<PaymentDetails> paymentDetails = paymentDetailsRepository.findAllByReceiptPaymentTypeAndSalePaymentType();

        // Group payments by invoice number
        Map<String, Double> paymentsByInvoice = paymentDetails.stream()
                .filter(pd -> pd.getPayment() != null && pd.getPayment().getReferenceId() != null)
                .collect(Collectors.groupingBy(pd -> pd.getPayment().getReferenceId(),
                        Collectors.summingDouble(PaymentDetails::getAmount)));

        // Calculate totals
        double totalSales = sales.stream().mapToDouble(Sale::getTotalAmount).sum();
        double totalReturns = salesReturns.stream().mapToDouble(SalesReturn::getRefundAmount).sum();
        double totalPaidAmount = sales.stream()
                .mapToDouble(sale -> paymentsByInvoice.getOrDefault(sale.getInvoiceNumber(), 0.0))
                .sum();
        double netSales = totalSales - totalReturns;
        double totalOutstanding = netSales - totalPaidAmount;

        // Prepare transaction details
        List<TransactionDetails> transactionDetails = prepareTransactionDetails(paymentDetails, salesReturns, custId);

        // Prepare response
        return buildCustomerResponse(customer, totalSales, totalReturns, totalPaidAmount, totalOutstanding, transactionDetails);
    }


    private List<TransactionDetails> prepareTransactionDetails(List<PaymentDetails> paymentDetails, List<SalesReturn> salesReturns, Integer customerId) {
        // Filter payment details for the specific customer
        List<PaymentDetails> customerPayments = paymentDetails.stream()
                .filter(payment -> payment.getPayment().getCustomer().getCustId().equals(customerId))
                .toList();

        // Filter sales returns for the specific customer
        List<SalesReturn> customerSalesReturns = salesReturns.stream()
                .filter(salesReturn -> salesReturn.getCustomerId().equals(customerId))
                .toList();

        // Map sale transactions
        List<TransactionDetails> saleTransactions = customerPayments.stream()
                .collect(Collectors.groupingBy(payment -> payment.getPayment().getReferenceId()))
                .entrySet()
                .stream()
                .map(entry -> {
                    String referenceId = entry.getKey();
                    List<PaymentDetails> payments = entry.getValue();

                    // Calculate total sale amount and cumulative payments
                    double totalSaleAmount = payments.get(0).getPayment().getTotalAmount();
                    double cumulativePayments = payments.stream().mapToDouble(PaymentDetails::getAmount).sum();

                    TransactionDetails details = new TransactionDetails();
                    details.setDate(payments.get(0).getPayment().getPaymentDate());
                    details.setType("Sale");
                    details.setInvoiceNo(referenceId);
                    details.setDebit(totalSaleAmount);
                    details.setCredit(0);
                    details.setBalance(totalSaleAmount - cumulativePayments);
                    return details;
                }).toList();

        // Map payment transactions
        List<TransactionDetails> paymentTransactions = customerPayments.stream().map(payment -> {
            TransactionDetails details = new TransactionDetails();
            details.setDate(payment.getPaymentDate());
            details.setType("Payment");
            details.setInvoiceNo(payment.getPayment().getReferenceId());
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
        List<TransactionDetails> returnTransactions = customerSalesReturns.stream().map(salesReturn -> {
            TransactionDetails details = new TransactionDetails();
            details.setDate(salesReturn.getReturnDate());
            details.setType("Return");
            details.setInvoiceNo(salesReturn.getInvoiceNumber());
            details.setDebit(0);
            details.setCredit(salesReturn.getRefundAmount());
            details.setBalance(0); // Temporary, will calculate later
            return details;
        }).toList();

        // Combine all transactions
        paymentTransactions.addAll(returnTransactions);
        paymentTransactions.addAll(saleTransactions);

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

        // Sort by date (descending) and type
//        List<TransactionDetails> sortedTransactions = paymentTransactions.stream()
//                .sorted(Comparator.comparing(TransactionDetails::getDate).reversed() // Sort by date in descending order
//                        .thenComparing(TransactionDetails::getType)) // Secondary sort by type
//                .collect(Collectors.toList());
//
//// Calculate running balance
//        double runningBalance = 0.0;
//        for (int i = sortedTransactions.size() - 1; i >= 0; i--) { // Process in reverse order
//            TransactionDetails transaction = sortedTransactions.get(i);
//            runningBalance += transaction.getCredit() - transaction.getDebit();
//            transaction.setBalance(runningBalance);
//        }

        return sortedTransactions;
    }

    // Helper method to build the customer response
    private CustomerPaymentDetailsResponse buildCustomerResponse(Customer customer, double totalSales, double totalReturns, double totalPaidAmount, double totalOutstanding, List<TransactionDetails> transactionDetails) {
        CustomerPaymentDetailsResponse response = new CustomerPaymentDetailsResponse();
        response.setCustomerId(customer.getCustId());
        response.setCustomerName(customer.getName());
        response.setTotalSales(totalSales);
        response.setTotalReturns(totalReturns);
        response.setOutstanding(totalOutstanding);
        response.setTotalPayments(totalPaidAmount);
        response.setTransactionDetails(transactionDetails);
        return response;
    }

    @Override
    public List<CustomerResponse> getAllCustomerDetailsWithSummary() {
        // Step 1: Fetch all customers
        List<Customer> customers = customerRepository.findAll();

        // Step 2: Fetch all sales with customers
        List<Sale> allSales = saleRepository.findAllWithCustomer();
        Map<Integer, List<Sale>> salesByCustomer = allSales.stream()
                .collect(Collectors.groupingBy(s -> s.getCustomer().getCustId()));

        // Step 3: Fetch all returns
        List<SalesReturn> allReturns = salesReturnRepository.findAllWithCustomer();
        Map<Integer, Double> returnsByCustomer = allReturns.stream()
                .collect(Collectors.groupingBy(SalesReturn::getCustomerId,
                        Collectors.summingDouble(SalesReturn::getRefundAmount)));

        // Step 4: Fetch all payments with invoices
        List<PaymentDetails> allPayments = paymentDetailsRepository.findAllByReceiptPaymentTypeAndSalePaymentType();
        Map<String, Double> paymentsByInvoice = allPayments.stream()
                .filter(pd -> pd.getPayment() != null && pd.getPayment().getReferenceId() != null)
                .collect(Collectors.groupingBy(pd -> pd.getPayment().getReferenceId(),
                        Collectors.summingDouble(PaymentDetails::getAmount)));

        // Step 5: Map customers to response
        return customers.stream().map(customer -> {
            List<Sale> customerSales = salesByCustomer.getOrDefault(customer.getCustId(), Collections.emptyList());

            double totalSales = customerSales.stream()
                    .mapToDouble(Sale::getTotalAmount)
                    .sum();

            double totalReturns = returnsByCustomer.getOrDefault(customer.getCustId(), 0.0);

            double totalPaid = customerSales.stream()
                    .mapToDouble(sale -> paymentsByInvoice.getOrDefault(sale.getInvoiceNumber(), 0.0))
                    .sum();

            double outstanding = totalSales - totalReturns - totalPaid;

            CustomerResponse response = new CustomerResponse();
            response.setCustId(customer.getCustId());
            response.setName(customer.getName());
            response.setPhone(customer.getPhone());
            response.setAddress(customer.getAddress());
            response.setEmail(customer.getEmail());
            response.setTotalSales(totalSales);
            response.setTotalReturns(totalReturns);
            response.setTotalPayments(totalPaid);
            response.setTotalOutstanding(outstanding);

            return response;
        }).toList();
    }


}
