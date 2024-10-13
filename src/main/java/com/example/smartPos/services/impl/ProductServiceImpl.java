package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.responses.BatchDetailsResponse;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.BatchRepository;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.services.IProductService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.ProductConstants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final BatchRepository batchRepository;

    public ProductServiceImpl(ProductRepository productRepository, BatchRepository batchRepository) {
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll().stream().map(product -> {
            ProductResponse prodResp = new ProductResponse();
            prodResp.setProductId(product.getProductId());
            prodResp.setCatId(product.getCatId());
            prodResp.setProductName(product.getProductName());
            prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
            prodResp.setBarCode(product.getBarcode());
            prodResp.setSku(product.getSku());
            prodResp.setBarCodeType(product.getBarCodeType());
            prodResp.setProductType(product.getProductType());
            prodResp.setProductStatus(product.getProductStatus());
            prodResp.setDescription(product.getDescription());
            prodResp.setStockManagementEnable(product.getStockManagementEnable());
            prodResp.setSalePrice(product.getSalePrice());
            prodResp.setWholeSalePrice(product.getWholeSalePrice());
            prodResp.setLowQty(product.getLowQty());
            prodResp.setExpDateAvailable(product.getExpDateAvailable());
            prodResp.setExpDate(product.getExpDate());
            prodResp.setTaxGroup(product.getTaxGroup());
            prodResp.setTaxType(product.getTaxType());
            prodResp.setImgUrl(product.getImgUrl());
            prodResp.setBatchNo(product.getBatchNo());
            prodResp.setStatus(product.getStatus());
            return prodResp;
        }).toList();
    }

    @Override
    public List<ProductResponse> getProductsByName(String prodName) {
        return productRepository.findAllByProductName(prodName).stream().map(product -> {
            ProductResponse prodResp = new ProductResponse();
            prodResp.setProductId(product.getProductId());
            prodResp.setCatId(product.getCatId());
            prodResp.setProductName(product.getProductName());
            prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
            prodResp.setBarCode(product.getBarcode());
            prodResp.setSku(product.getSku());
            prodResp.setBarCodeType(product.getBarCodeType());
            prodResp.setProductType(product.getProductType());
            prodResp.setProductStatus(product.getProductStatus());
            prodResp.setDescription(product.getDescription());
            prodResp.setStockManagementEnable(product.getStockManagementEnable());
            prodResp.setSalePrice(product.getSalePrice());
            prodResp.setWholeSalePrice(product.getWholeSalePrice());
            prodResp.setLowQty(product.getLowQty());
            prodResp.setExpDateAvailable(product.getExpDateAvailable());
            prodResp.setExpDate(product.getExpDate());
            prodResp.setTaxGroup(product.getTaxGroup());
            prodResp.setTaxType(product.getTaxType());
            prodResp.setImgUrl(product.getImgUrl());
            prodResp.setBatchNo(product.getBatchNo());
            prodResp.setStatus(product.getStatus());
            return prodResp;
        }).toList();
    }

    @Override
    public ProductResponse getProductByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
        );
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCatId(product.getCatId());
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
        prodResp.setSalePrice(product.getSalePrice());
        prodResp.setWholeSalePrice(product.getWholeSalePrice());
        prodResp.setLowQty(product.getLowQty());
        prodResp.setExpDateAvailable(product.getExpDateAvailable());
        prodResp.setExpDate(product.getExpDate());
        prodResp.setTaxGroup(product.getTaxGroup());
        prodResp.setTaxType(product.getTaxType());
        prodResp.setImgUrl(product.getImgUrl());
        prodResp.setBatchNo(product.getBatchNo());
        prodResp.setStatus(product.getStatus());
        return prodResp;
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
        );
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCatId(product.getCatId());
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
        prodResp.setSalePrice(product.getSalePrice());
        prodResp.setWholeSalePrice(product.getWholeSalePrice());
        prodResp.setLowQty(product.getLowQty());
        prodResp.setExpDateAvailable(product.getExpDateAvailable());
        prodResp.setExpDate(product.getExpDate());
        prodResp.setTaxGroup(product.getTaxGroup());
        prodResp.setTaxType(product.getTaxType());
        prodResp.setImgUrl(product.getImgUrl());
        prodResp.setBatchNo(product.getBatchNo());
        prodResp.setStatus(product.getStatus());
        return prodResp;
    }

    @Override
    public ProductResponse getProductById(String barcode) {
        Product product = productRepository.findByBarcode(barcode).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
        );
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCatId(product.getCatId());
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
        prodResp.setSalePrice(product.getSalePrice());
        prodResp.setWholeSalePrice(product.getWholeSalePrice());
        prodResp.setLowQty(product.getLowQty());
        prodResp.setExpDateAvailable(product.getExpDateAvailable());
        prodResp.setExpDate(product.getExpDate());
        prodResp.setTaxGroup(product.getTaxGroup());
        prodResp.setTaxType(product.getTaxType());
        prodResp.setImgUrl(product.getImgUrl());
        prodResp.setBatchNo(product.getBatchNo());
        prodResp.setStatus(product.getStatus());
        return prodResp;
    }

    public ProductResponse getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
        );
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCatId(product.getCatId());
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
        prodResp.setSalePrice(product.getSalePrice());
        prodResp.setWholeSalePrice(product.getWholeSalePrice());
        prodResp.setLowQty(product.getLowQty());
        prodResp.setExpDateAvailable(product.getExpDateAvailable());
        prodResp.setExpDate(product.getExpDate());
        prodResp.setTaxGroup(product.getTaxGroup());
        prodResp.setTaxType(product.getTaxType());
        prodResp.setImgUrl(product.getImgUrl());
        prodResp.setBatchNo(product.getBatchNo());
        prodResp.setStatus(product.getStatus());
        return prodResp;
    }

    @Override
    public ProductResponse getProductByBarcodeOrSku(String barCodeOrSku) {

        Product product = productRepository.findByBarcodeOrSku(barCodeOrSku, barCodeOrSku).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
        );
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCatId(product.getCatId());
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
        prodResp.setSalePrice(product.getSalePrice());
        prodResp.setWholeSalePrice(product.getWholeSalePrice());
        prodResp.setLowQty(product.getLowQty());
        prodResp.setExpDateAvailable(product.getExpDateAvailable());
        prodResp.setExpDate(product.getExpDate());
        prodResp.setTaxGroup(product.getTaxGroup());
        prodResp.setTaxType(product.getTaxType());
        prodResp.setImgUrl(product.getImgUrl());
        prodResp.setBatchNo(product.getBatchNo());
        prodResp.setStatus(product.getStatus());
        return prodResp;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Optional<Product> productBySku = productRepository.findBySku(productRequest.getSku());
        if (productBySku.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_SKU);
        }

        Optional<Product> productByName = productRepository.findByProductName(productRequest.getProductName());
        if (productByName.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_PRODUCT);
        }

        Product savedProduct = productRepository.save(getSaveProduct(productRequest));

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId(savedProduct.getProductId());
        productResponse.setProductName(savedProduct.getProductName());
        productResponse.setSku(savedProduct.getSku());
        productResponse.setStatusCode(ProductConstants.STATUS_201);
        productResponse.setDesc(ProductConstants.MESSAGE_201);
        return productResponse;
    }

    private static Product getSaveProduct(ProductRequest productRequest) {
        Product saveProduct = new Product();
        saveProduct.setCatId(productRequest.getCatId());
        saveProduct.setProductName(productRequest.getProductName());
        saveProduct.setBarCodeAvailable(productRequest.getBarCodeAvailable());
        saveProduct.setBarcode(productRequest.getBarCode());
        saveProduct.setSku(productRequest.getSku());
        saveProduct.setBarCodeType(productRequest.getBarCodeType());
        saveProduct.setProductType(productRequest.getProductType());
        saveProduct.setProductStatus(productRequest.getProductStatus());
        saveProduct.setDescription(productRequest.getDescription());
        saveProduct.setStockManagementEnable(productRequest.getStockManagementEnable());
//        saveProduct.setSalePrice(productRequest.getSalePrice());
//        saveProduct.setWholeSalePrice(productRequest.getWholeSalePrice());
        saveProduct.setLowQty(productRequest.getLowQty());
        saveProduct.setExpDateAvailable(productRequest.getExpDateAvailable());
        saveProduct.setExpDate(productRequest.getExpDate());
        saveProduct.setTaxGroup(productRequest.getTaxGroup());
        saveProduct.setTaxType(productRequest.getTaxType());
        saveProduct.setImgUrl(productRequest.getImgUrl());
        saveProduct.setBatchNo(productRequest.getBatchNo());
        saveProduct.setStatus(productRequest.getStatus());
        saveProduct.setStatus(1);
        saveProduct.fillNew("ADMIN USER");
        return saveProduct;
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        ProductResponse updatedProductResponse = new ProductResponse();
        if (productRequest.getProductId() != null) {
            Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
            );
            product.setCatId(productRequest.getCatId());
            product.setProductName(productRequest.getProductName());
            product.setBarCodeAvailable(productRequest.getBarCodeAvailable());
            product.setBarcode(productRequest.getBarCode());
            product.setSku(productRequest.getSku());
            product.setBarCodeType(productRequest.getBarCodeType());
            product.setProductType(productRequest.getProductType());
            product.setProductStatus(productRequest.getProductStatus());
            product.setDescription(productRequest.getDescription());
            product.setStockManagementEnable(productRequest.getStockManagementEnable());
//            product.setSalePrice(productRequest.getSalePrice());
//            product.setWholeSalePrice(productRequest.getWholeSalePrice());
            product.setLowQty(productRequest.getLowQty());
            product.setExpDateAvailable(productRequest.getExpDateAvailable());
            product.setExpDate(productRequest.getExpDate());
            product.setTaxGroup(productRequest.getTaxGroup());
            product.setTaxType(productRequest.getTaxType());
            product.setImgUrl(productRequest.getImgUrl());
            product.setBatchNo(productRequest.getBatchNo());
            product.setStatus(productRequest.getStatus());
            product.setStatus(1);
            product.fillNew("ADMIN USER");
            Product updateProduct = productRepository.save(product);

            updatedProductResponse.setProductId(updateProduct.getProductId());
            updatedProductResponse.setCatId(updateProduct.getCatId());
            updatedProductResponse.setProductName(updateProduct.getProductName());
            updatedProductResponse.setBarCodeAvailable(updateProduct.getBarCodeAvailable());
            updatedProductResponse.setBarCode(updateProduct.getBarcode());
            updatedProductResponse.setSku(updateProduct.getSku());
            updatedProductResponse.setBarCodeType(updateProduct.getBarCodeType());
            updatedProductResponse.setProductType(updateProduct.getProductType());
            updatedProductResponse.setProductStatus(updateProduct.getProductStatus());
            updatedProductResponse.setDescription(updateProduct.getDescription());
            updatedProductResponse.setStockManagementEnable(updateProduct.getStockManagementEnable());
            updatedProductResponse.setSalePrice(updateProduct.getSalePrice());
            updatedProductResponse.setWholeSalePrice(updateProduct.getWholeSalePrice());
            updatedProductResponse.setLowQty(updateProduct.getLowQty());
            updatedProductResponse.setExpDateAvailable(updateProduct.getExpDateAvailable());
            updatedProductResponse.setExpDate(updateProduct.getExpDate());
            updatedProductResponse.setTaxGroup(updateProduct.getTaxGroup());
            updatedProductResponse.setTaxType(updateProduct.getTaxType());
            updatedProductResponse.setImgUrl(updateProduct.getImgUrl());
            updatedProductResponse.setBatchNo(updateProduct.getBatchNo());
            updatedProductResponse.setStatus(updateProduct.getStatus());

        }
        return updatedProductResponse;
    }

    public List<BatchDetailsResponse> getBatchDetailsByProductSku(String productSku) {
        return batchRepository.findAllBySku(productSku).stream().map(batch -> {
            BatchDetailsResponse batchDetails = new BatchDetailsResponse();
            batchDetails.setBatchId(batch.getBatchId());
            batchDetails.setBatchNumber(batch.getBatchNumber());
            batchDetails.setSku(batch.getSku());
            batchDetails.setUnitCost(batch.getUnitCost());
            batchDetails.setSupplier(batch.getSupplier());
            batchDetails.setInvoiceNumber(batch.getInvoiceNumber());
            batchDetails.setPurchaseDate(batch.getPurchaseDate());

            return batchDetails;
        }).toList();
    }
}
