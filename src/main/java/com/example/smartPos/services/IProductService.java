package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.responses.BatchDetailsResponse;
import com.example.smartPos.controllers.responses.ProductResponse;

import java.util.List;

public interface IProductService {
    public List<ProductResponse> getAllProducts();

    public List<ProductResponse> getProductsByName(String name);

    public ProductResponse getProductByBarcode(String barcode);

    public ProductResponse getProductById(String barcode);

    public ProductResponse getProductById(Integer id);

    public ProductResponse getProductBySku(String sku);

    public ProductResponse getProductByBarcodeOrSku(String barCodeOrSku);

    public ProductResponse createProduct(ProductRequest productRequest);

    public ProductResponse updateProduct(ProductRequest productRequest);

    public List<BatchDetailsResponse> getBatchDetailsByProductSku(String id);
}
