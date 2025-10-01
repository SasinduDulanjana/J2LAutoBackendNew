package com.example.smartPos.controllers.responses;

public class ChequeDetailsResponse {
    private String chequeNo;
    private String bankName;

    private String type;

    private String customerOrSupplierName;

    private String issueDate;

    private String dueDate;

    private String status;
    private Double amount;


    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerOrSupplierName() {
        return customerOrSupplierName;
    }

    public void setCustomerOrSupplierName(String customerOrSupplierName) {
        this.customerOrSupplierName = customerOrSupplierName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}