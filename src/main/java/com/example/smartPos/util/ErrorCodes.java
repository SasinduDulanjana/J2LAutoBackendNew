package com.example.smartPos.util;

public enum ErrorCodes {
    DATABASE_ERROR("01", "Unable to access database"),
    SYSTEM_OFFLINE("98", "System Offline"),
    SYSTEM_ERROR_AND_TIMEOUT("99", "System Error / Timeout"),
    INVALID_ACCOUNT("06", "Invalid Account"),
    INVALID_ACCOUNT_OTHER("52", "Invalid Account - Other"),
    UNABLE_TO_ACCEPT_TRANSACTION("05", "Unable to Accept Transaction"),
    DUPLICATE_TRANSACTION("19", "Duplicate Transaction"),
    SYSTEM_ERROR("96", "System Error"),
    INVALID_TRANSACTION("12", "Invalid Transaction"),
    INVALID_REFERENCE_NO("05", "Invalid Reference Number"),
    REFUND_ALREADY_INITIATED("09", "Refund already initiated"),

    ALREADY_EXISTS_SUPPLIER("19", "Supplier Already Exists"),
    SUPPLIER_NOT_FOUND("18", "Supplier doesn't exist"),

    ALREADY_EXISTS_CUSTOMER("29", "Customer Already Exists"),
    CUSTOMER_NOT_FOUND("28", "Customer doesn't exist"),

    ALREADY_EXISTS_CATEGORY("39", "Category Already Exists"),
    CATEGORY_NOT_FOUND("38", "Category doesn't exist"),

    ALREADY_EXISTS_PRODUCT("49", "Product Already Exists"),

    ALREADY_EXISTS_SKU("47", "SKU Already Exists"),
    PRODUCT_NOT_FOUND("48", "Product doesn't exist"),

    ALREADY_EXISTS_INVOICE_NUMBER("57", "Invoice Number Already Exists"),
    INVOICE_NUMBER_NOT_FOUND("58", "Invoice Number doesn't exist"),
    PURCHASE_NOT_FOUND("59", "Purchase doesn't exist"),

    INVOICE_NUMBER_NOT_FOUND_FOR_ORDER("68", "Invoice Number doesn't exist"),
    ORDER_NOT_FOUND("69", "Order doesn't exist"),

    USER_NAME_NOT_FOUND("79", "Username Not Found"),

    INVENTORY_NOT_FOUND("89", "Inventory Not Found"),
    SALE_NOT_FOUND("88", "Sale Not Found"),

    PRODUCT_BATCH_NOT_FOUND("60", "Product Batch doesn't exist"),

    BATCH_NOT_FOUND("61", "Batch doesn't exist"),

    PAYMENT_NOT_FOUND("62", "Payment doesn't exist");


    public final String code;
    public final String description;

    ErrorCodes(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
