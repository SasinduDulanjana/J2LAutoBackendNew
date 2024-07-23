package com.example.smartPos.repositories.model;

import com.example.smartPos.repositories.model.embeddable.PurchaseProductId;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "PURCHASE_PRODUCT")
public class PurchaseProduct {

    @EmbeddedId
    private PurchaseProductId id;

    @ManyToOne
    @MapsId("purchaseId")
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

//    private int quantity;

    public PurchaseProductId getId() {
        return id;
    }

    public void setId(PurchaseProductId id) {
        this.id = id;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
