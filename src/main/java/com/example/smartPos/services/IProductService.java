package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.requests.VehicleRequest;
import com.example.smartPos.controllers.responses.BatchDetailsResponse;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.VehicleResponse;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getAllProducts();

    List<ProductResponse> getAllProductsByBatchWise();

    List<ProductResponse> getProductsByName(String name);

    ProductResponse getProductByBarcode(String barcode);

    ProductResponse getProductById(String barcode);

    ProductResponse getProductById(Integer id);

    ProductResponse getProductBySku(String sku);

    ProductResponse getProductByBarcodeOrSku(String barCodeOrSku);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(ProductRequest productRequest);

    List<BatchDetailsResponse> getBatchDetailsByProductSku(String id);

    List<ProductResponse> getProductsByCategory(String categoryId);

    Double getAvailableQuantity(String skuId, String batchNumber);

    void deleteProduct(Integer prodId);

    List<VehicleResponse> availableVehicles();

    VehicleResponse createVehicle(VehicleRequest request);
}
