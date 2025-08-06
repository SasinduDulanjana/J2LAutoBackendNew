package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByProductName(String name);

    Optional<Product> findByBarcode(String barcode);

    Optional<Product> findByProductName(String name);

    Optional<Product> findBySku(String sku);

    Product findByProductIdAndSku(Integer id, String sku);

    Optional<Product> findByBarcodeOrSku(String barcode, String sku);

    List<Product> findAllByCatId(Integer categoryId);

    List<Product> findAllByStatus(Integer status);

}
