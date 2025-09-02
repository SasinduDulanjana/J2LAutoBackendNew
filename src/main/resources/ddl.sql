-- DDL and Sample Data for smartPos Entities

-- Clear existing data
DELETE FROM PRODUCT_BATCH;
DELETE FROM PURCHASE_PRODUCT;
DELETE FROM SALE_PRODUCT;
DELETE FROM PAYMENT;
DELETE FROM SALE;
DELETE FROM PURCHASE;
DELETE FROM INVENTORY;
DELETE FROM PRODUCTS;
DELETE FROM BATCH;
DELETE FROM SUPPLIER;
DELETE FROM CUSTOMER;
DELETE FROM CATEGORY;
DELETE FROM USERS;
DELETE FROM USER_ROLE;
DELETE FROM ROLE;
DELETE FROM role_permissions;
DELETE FROM feature;

-- Feature Table
CREATE TABLE feature (
    id INT PRIMARY KEY AUTO_INCREMENT,
    feature_name VARCHAR(255),
    description VARCHAR(255)
);
INSERT INTO feature (feature_name, description) VALUES
('Inventory', 'Manage inventory items'),
('Sales', 'Handle sales transactions'),
('Purchase', 'Track purchases'),
('Reporting', 'Generate business reports'),
('User Management', 'Manage system users');

-- PAYMENT Table
CREATE TABLE PAYMENT (
    payment_id INT PRIMARY KEY,
    sale_id VARCHAR(255),
    amount VARCHAR(255),
    payment_method VARCHAR(255)
);
INSERT INTO PAYMENT (payment_id, sale_id, amount, payment_method) VALUES
(1, 'S001', '1000.00', 'Cash'),
(2, 'S002', '1500.00', 'Card'),
(3, 'S003', '2000.00', 'Online'),
(4, 'S004', '500.00', 'Cash'),
(5, 'S005', '750.00', 'Card');

-- PRODUCT_BATCH Table
CREATE TABLE PRODUCT_BATCH (
    id INT PRIMARY KEY,
    product_id INT,
    batch_id INT,
    qty DOUBLE,
    unit_cost DOUBLE
);
INSERT INTO PRODUCT_BATCH (id, product_id, batch_id, qty, unit_cost) VALUES
(1, 1, 1, 100, 10.5),
(2, 2, 1, 50, 12.0),
(3, 3, 2, 200, 8.75),
(4, 4, 3, 150, 9.0),
(5, 5, 2, 80, 11.25);

-- PRODUCTS Table
CREATE TABLE PRODUCTS (
    product_id INT PRIMARY KEY,
    cat_id INT,
    product_name VARCHAR(255),
    is_bar_code_available BOOLEAN,
    barcode VARCHAR(255),
    sku VARCHAR(255),
    bar_code_type VARCHAR(255),
    product_type VARCHAR(255),
    product_status VARCHAR(255),
    description VARCHAR(255),
    is_stock_management_enable BOOLEAN,
    low_qty VARCHAR(255),
    is_exp_date_available BOOLEAN,
    exp_date VARCHAR(255),
    tax_group VARCHAR(255),
    tax_type VARCHAR(255),
    img_url VARCHAR(255),
    batch_no VARCHAR(255),
    remaining_qty DOUBLE,
    status INT,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO PRODUCTS (product_id, cat_id, product_name, is_bar_code_available, barcode, sku, bar_code_type, product_type, product_status, description, is_stock_management_enable, low_qty, is_exp_date_available, exp_date, tax_group, tax_type, img_url, batch_no, remaining_qty, status, add_by, modified_by, added_date, modified_date) VALUES
(1, 1, 'Laptop', true, '123456789', 'SKU001', 'EAN', 'Electronics', 'Active', 'High-end laptop', true, '10', false, NULL, 'VAT', 'Standard', 'laptop.jpg', 'B001', 50, 1, 'admin', 'admin', NOW(), NOW()),
(2, 2, 'Mouse', true, '987654321', 'SKU002', 'UPC', 'Accessories', 'Active', 'Wireless mouse', true, '20', false, NULL, 'VAT', 'Reduced', 'mouse.jpg', 'B002', 100, 1, 'admin', 'admin', NOW(), NOW()),
(3, 1, 'Monitor', false, NULL, 'SKU003', 'EAN', 'Electronics', 'Inactive', '24 inch monitor', true, '5', true, '2025-12-31', 'VAT', 'Standard', 'monitor.jpg', 'B003', 30, 0, 'admin', 'admin', NOW(), NOW()),
(4, 3, 'Keyboard', true, '112233445', 'SKU004', 'UPC', 'Accessories', 'Active', 'Mechanical keyboard', true, '15', false, NULL, 'VAT', 'Standard', 'keyboard.jpg', 'B004', 60, 1, 'admin', 'admin', NOW(), NOW()),
(5, 2, 'Printer', false, NULL, 'SKU005', 'EAN', 'Electronics', 'Active', 'Laser printer', true, '8', true, '2026-01-15', 'VAT', 'Reduced', 'printer.jpg', 'B005', 25, 1, 'admin', 'admin', NOW(), NOW());

-- PURCHASE Table
CREATE TABLE PURCHASE (
    purchase_id INT PRIMARY KEY,
    supplier_id INT,
    purchase_name VARCHAR(255),
    invoice_number VARCHAR(255),
    delivery_time VARCHAR(255),
    invoice_date VARCHAR(255),
    connection_status VARCHAR(255),
    payment_status VARCHAR(255),
    product_type VARCHAR(255),
    total_cost DOUBLE,
    paid_amount DOUBLE,
    is_fully_paid BOOLEAN,
    status INT,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO PURCHASE (purchase_id, supplier_id, purchase_name, invoice_number, delivery_time, invoice_date, connection_status, payment_status, product_type, total_cost, paid_amount, is_fully_paid, status, add_by, modified_by, added_date, modified_date) VALUES
(1, 1, 'Purchase Laptop', 'INV001', '2 days', '2025-08-01', 'Connected', 'Paid', 'Electronics', 5000.00, 5000.00, true, 1, 'admin', 'admin', NOW(), NOW()),
(2, 2, 'Purchase Mouse', 'INV002', '1 day', '2025-08-02', 'Connected', 'Unpaid', 'Accessories', 1000.00, 500.00, false, 1, 'admin', 'admin', NOW(), NOW()),
(3, 1, 'Purchase Monitor', 'INV003', '3 days', '2025-08-03', 'Disconnected', 'Paid', 'Electronics', 3000.00, 3000.00, true, 1, 'admin', 'admin', NOW(), NOW()),
(4, 3, 'Purchase Keyboard', 'INV004', '2 days', '2025-08-04', 'Connected', 'Paid', 'Accessories', 1500.00, 1500.00, true, 1, 'admin', 'admin', NOW(), NOW()),
(5, 2, 'Purchase Printer', 'INV005', '1 day', '2025-08-05', 'Disconnected', 'Unpaid', 'Electronics', 2500.00, 1000.00, false, 1, 'admin', 'admin', NOW(), NOW());

-- PURCHASE_PRODUCT Table
CREATE TABLE PURCHASE_PRODUCT (
    purchase_id INT,
    product_id INT,
    PRIMARY KEY (purchase_id, product_id)
);
INSERT INTO PURCHASE_PRODUCT (purchase_id, product_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- ROLE Table
CREATE TABLE ROLE (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description VARCHAR(255),
    status VARCHAR(255)
);
INSERT INTO ROLE (name, description, status) VALUES
('ADMIN', 'System administrator', 'Active'),
('MANAGER', 'Store manager', 'Active'),
('CASHIER', 'Handles sales', 'Active'),
('SUPPLIER', 'Manages supplies', 'Inactive'),
('CUSTOMER', 'End user', 'Active');

-- role_permissions Table
CREATE TABLE role_permissions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role VARCHAR(255),
    feature VARCHAR(255),
    enabled BOOLEAN
);
INSERT INTO role_permissions (role, feature, enabled) VALUES
('Admin', 'Inventory', true),
('Manager', 'Sales', true),
('Cashier', 'Sales', true),
('Supplier', 'Purchase', true),
('Customer', 'Reporting', false);

-- SALE Table
CREATE TABLE SALE (
    sale_id INT PRIMARY KEY,
    cust_id INT,
    user_id INT,
    sale_date DATETIME,
    total_amount DOUBLE,
    sub_total DOUBLE,
    payment_method VARCHAR(255),
    bill_wise_discount_percentage DOUBLE,
    bill_wise_discount_total_amount DOUBLE,
    line_wise_discount_total_amount DOUBLE,
    invoice_number VARCHAR(255),
    status INT,
    is_fully_paid BOOLEAN,
    is_hold BOOLEAN,
    paid_amount DOUBLE,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO SALE (sale_id, cust_id, user_id, sale_date, total_amount, sub_total, payment_method, bill_wise_discount_percentage, bill_wise_discount_total_amount, line_wise_discount_total_amount, invoice_number, status, is_fully_paid, is_hold, paid_amount, add_by, modified_by, added_date, modified_date) VALUES
(1, 1, 1, NOW(), 1000.00, 950.00, 'Cash', 5.0, 50.00, 0.00, 'INV001', 1, true, false, 1000.00, 'admin', 'admin', NOW(), NOW()),
(2, 2, 2, NOW(), 1500.00, 1400.00, 'Card', 7.0, 100.00, 0.00, 'INV002', 1, true, false, 1500.00, 'admin', 'admin', NOW(), NOW()),
(3, 3, 1, NOW(), 2000.00, 1900.00, 'Online', 5.0, 100.00, 0.00, 'INV003', 1, true, false, 2000.00, 'admin', 'admin', NOW(), NOW()),
(4, 4, 2, NOW(), 500.00, 480.00, 'Cash', 4.0, 20.00, 0.00, 'INV004', 1, true, false, 500.00, 'admin', 'admin', NOW(), NOW()),
(5, 5, 1, NOW(), 750.00, 700.00, 'Card', 6.0, 50.00, 0.00, 'INV005', 1, true, false, 750.00, 'admin', 'admin', NOW(), NOW());

-- SALE_PRODUCT Table
CREATE TABLE SALE_PRODUCT (
    sale_product_id INT PRIMARY KEY,
    sale_id INT,
    product_id INT,
    quantity INT,
    discounted_total DOUBLE,
    discount_percentage DOUBLE,
    discount_amount DOUBLE
);
INSERT INTO SALE_PRODUCT (sale_product_id, sale_id, product_id, quantity, discounted_total, discount_percentage, discount_amount) VALUES
(1, 1, 1, 2, 950.00, 5.0, 50.00),
(2, 2, 2, 1, 1400.00, 7.0, 100.00),
(3, 3, 3, 3, 1900.00, 5.0, 100.00),
(4, 4, 4, 1, 480.00, 4.0, 20.00),
(5, 5, 5, 2, 700.00, 6.0, 50.00);

-- SUPPLIER Table
CREATE TABLE SUPPLIER (
    sup_id INT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    address VARCHAR(255),
    status INT,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO SUPPLIER (sup_id, name, email, phone, address, status, add_by, modified_by, added_date, modified_date) VALUES
(1, 'ABC Suppliers', 'abc@suppliers.com', '0771234567', 'Colombo', 1, 'admin', 'admin', NOW(), NOW()),
(2, 'XYZ Traders', 'xyz@traders.com', '0777654321', 'Kandy', 1, 'admin', 'admin', NOW(), NOW()),
(3, 'Tech World', 'tech@world.com', '0771122334', 'Galle', 1, 'admin', 'admin', NOW(), NOW()),
(4, 'Office Mart', 'office@mart.com', '0779988776', 'Matara', 1, 'admin', 'admin', NOW(), NOW()),
(5, 'Print Hub', 'print@hub.com', '0775566778', 'Jaffna', 1, 'admin', 'admin', NOW(), NOW());

-- USERS Table
CREATE TABLE USERS (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    status VARCHAR(255)
);
INSERT INTO USERS (username, password, email, status) VALUES
('admin', 'admin123', 'admin@smartpos.com', 'Active'),
('manager', 'manager123', 'manager@smartpos.com', 'Active'),
('cashier', 'cashier123', 'cashier@smartpos.com', 'Active'),
('supplier', 'supplier123', 'supplier@smartpos.com', 'Inactive'),
('customer', 'customer123', 'customer@smartpos.com', 'Active');
CREATE TABLE USER_ROLE (
    user_id INT,
    role_id INT
);
INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- INVENTORY Table
CREATE TABLE INVENTORY (
    id INT PRIMARY KEY,
    sku VARCHAR(255),
    batch_number VARCHAR(255),
    qty DOUBLE,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO INVENTORY (id, sku, batch_number, qty, add_by, modified_by, added_date, modified_date) VALUES
(1, 'SKU001', 'B001', 50, 'admin', 'admin', NOW(), NOW()),
(2, 'SKU002', 'B002', 100, 'admin', 'admin', NOW(), NOW()),
(3, 'SKU003', 'B003', 30, 'admin', 'admin', NOW(), NOW()),
(4, 'SKU004', 'B004', 60, 'admin', 'admin', NOW(), NOW()),
(5, 'SKU005', 'B005', 25, 'admin', 'admin', NOW(), NOW());

-- CUSTOMER Table
CREATE TABLE CUSTOMER (
    cust_id INT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    address VARCHAR(255),
    status INT,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO CUSTOMER (cust_id, name, email, phone, address, status, add_by, modified_by, added_date, modified_date) VALUES
(1, 'John Doe', 'john@doe.com', '0771111111', 'Colombo', 1, 'admin', 'admin', NOW(), NOW()),
(2, 'Jane Smith', 'jane@smith.com', '0772222222', 'Kandy', 1, 'admin', 'admin', NOW(), NOW()),
(3, 'Bob Lee', 'bob@lee.com', '0773333333', 'Galle', 1, 'admin', 'admin', NOW(), NOW()),
(4, 'Alice Brown', 'alice@brown.com', '0774444444', 'Matara', 1, 'admin', 'admin', NOW(), NOW()),
(5, 'Charlie Black', 'charlie@black.com', '0775555555', 'Jaffna', 1, 'admin', 'admin', NOW(), NOW());

-- CATEGORY Table
CREATE TABLE CATEGORY (
    cat_id INT PRIMARY KEY,
    name VARCHAR(255),
    cat_desc VARCHAR(255),
    parent INT,
    status INT,
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO CATEGORY (cat_id, name, cat_desc, parent, status, add_by, modified_by, added_date, modified_date) VALUES
(1, 'Electronics', 'Electronic items', NULL, 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Accessories', 'Computer accessories', 1, 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Office', 'Office equipment', NULL, 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Printing', 'Printing devices', 3, 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Furniture', 'Office furniture', NULL, 1, 'admin', 'admin', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- BATCH Table
CREATE TABLE BATCH (
    batch_id INT PRIMARY KEY,
    sku VARCHAR(255),
    batch_number VARCHAR(255),
    unit_cost DOUBLE,
    retail_price DOUBLE,
    wholesale_price DOUBLE,
    supplier INT,
    purchase_date VARCHAR(255),
    invoice_number VARCHAR(255),
    add_by VARCHAR(255),
    modified_by VARCHAR(255),
    added_date DATETIME,
    modified_date DATETIME
);
INSERT INTO BATCH (batch_id, sku, batch_number, unit_cost, retail_price, wholesale_price, supplier, purchase_date, invoice_number, add_by, modified_by, added_date, modified_date) VALUES
(1, 'SKU001', 'B001', 10.5, 12.0, 11.0, 1, '2025-08-01', 'INV001', 'admin', 'admin', NOW(), NOW()),
(2, 'SKU002', 'B002', 8.75, 10.0, 9.5, 2, '2025-08-02', 'INV002', 'admin', 'admin', NOW(), NOW()),
(3, 'SKU003', 'B003', 9.0, 11.0, 10.0, 1, '2025-08-03', 'INV003', 'admin', 'admin', NOW(), NOW()),
(4, 'SKU004', 'B004', 11.25, 13.0, 12.0, 3, '2025-08-04', 'INV004', 'admin', 'admin', NOW(), NOW()),
(5, 'SKU005', 'B005', 12.5, 14.0, 13.0, 2, '2025-08-05', 'INV005', 'admin', 'admin', NOW(), NOW());
