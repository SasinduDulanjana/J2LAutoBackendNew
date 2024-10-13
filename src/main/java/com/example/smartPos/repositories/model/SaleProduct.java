package com.example.smartPos.repositories.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SALE_PRODUCT")
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer saleProductId;

    @ManyToOne
    @JoinColumn(name = "SALE_ID")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "DISCOUNTED_TOTAL")
    private Double discountedTotal;

    @Column(name = "DISCOUNT_PERCENTAGE")
    private Double discountPercentage;

    @Column(name = "DISCOUNT_AMOUNT")
    private Double discountAmount;
    public Integer getSaleProductId() {
        return saleProductId;
    }

    public void setSaleProductId(Integer saleProductId) {
        this.saleProductId = saleProductId;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(Double discountedTotal) {
        this.discountedTotal = discountedTotal;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

}
