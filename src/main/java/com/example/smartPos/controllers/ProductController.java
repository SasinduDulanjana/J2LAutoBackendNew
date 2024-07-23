package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.responses.CommonResponse;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.services.IProductService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/api/getAllProducts")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/api/getProductsByName/{name}")
    public List<ProductResponse> getProductsByName(@PathVariable String name) {
        return productService.getProductsByName(name);
    }

    @GetMapping(path = "/api/getProductByBarcode/{barcode}")
    public ResponseEntity<CommonResponse> getProductsByBarcode(@PathVariable String barcode) {
        ProductResponse productResponse = productService.getProductByBarcode(barcode);
        return ResponseCreator.success(productResponse);
    }

    @GetMapping(path = "/api/getProductBySku/{sku}")
    public ResponseEntity<CommonResponse> getProductBySku(@PathVariable String sku) {
        ProductResponse productResponse = productService.getProductBySku(sku);
        return ResponseCreator.success(productResponse);
    }

    @PostMapping(path = "/api/createProduct")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse savedProduct = productService.createProduct(productRequest);
        return ResponseCreator.success(savedProduct);
    }

    @PostMapping(path = "/api/updateProduct")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(productRequest);
        return ResponseCreator.success(updatedProduct);
    }

}
