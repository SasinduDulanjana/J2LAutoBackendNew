package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.requests.VehicleRequest;
import com.example.smartPos.controllers.responses.BatchDetailsResponse;
import com.example.smartPos.controllers.responses.BatchDetails;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.VehicleResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.mapper.CategoryMapper;
import com.example.smartPos.mapper.VehicleMapper;
import com.example.smartPos.repositories.BatchRepository;
import com.example.smartPos.repositories.CategoryRepository;
import com.example.smartPos.repositories.ProductRepository;
import com.example.smartPos.repositories.VehicleRepository;
import com.example.smartPos.repositories.model.Batch;
import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Vehicle;
import com.example.smartPos.services.IProductService;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.ProductConstants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final BatchRepository batchRepository;

    private final CategoryRepository categoryRepository;

    private final VehicleRepository vehicleRepository;

    private final CategoryMapper categoryMapper;

    private final VehicleMapper vehicleMapper;

    public ProductServiceImpl(ProductRepository productRepository, BatchRepository batchRepository, CategoryRepository categoryRepository, VehicleRepository vehicleRepository, CategoryMapper categoryMapper, VehicleMapper vehicleMapper) {
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
        this.categoryRepository = categoryRepository;
        this.vehicleRepository = vehicleRepository;
        this.categoryMapper = categoryMapper;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        List<Product> productlist = productRepository.findAllByStatus(1);
        return productlist.stream().map(product -> {
            ProductResponse prodResp = new ProductResponse();
            prodResp.setProductId(product.getProductId());
            prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
            prodResp.setProductName(product.getProductName());
            prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
            prodResp.setBarCode(product.getBarcode());
            prodResp.setSku(product.getSku());
            prodResp.setBarCodeType(product.getBarCodeType());
            prodResp.setProductType(product.getProductType());
            prodResp.setProductStatus(product.getProductStatus());
            prodResp.setDescription(product.getDescription());
            prodResp.setStockManagementEnable(product.getStockManagementEnable());
            prodResp.setLowQty(product.getLowQty());
            prodResp.setExpDateAvailable(product.getExpDateAvailable());
            prodResp.setExpDate(product.getExpDate());
            prodResp.setTaxGroup(product.getTaxGroup());
            prodResp.setTaxType(product.getTaxType());
            prodResp.setImgUrl(product.getImgUrl());
            prodResp.setBatchNo(product.getBatchNo());
            prodResp.setStatus(product.getStatus());
            prodResp.setPartNumber(product.getPartNumber());
            prodResp.setBrandName(product.getBrandName());
            prodResp.setVehicleList(new ArrayList<>(product.getVehicles()));
            return prodResp;
        }).toList();
    }

    @Override
    public List<ProductResponse> getAllProductsByBatchWise() {

        List<Product> productList = productRepository.findAllByStatus(1);

        // Fetch all batch details for the products in a single query
        Map<String, List<Batch>> batchDetailsMap = batchRepository.findAllBySkuIn(
                productList.stream().map(Product::getSku).toList()
        ).stream().collect(Collectors.groupingBy(Batch::getSku));

        return productList.stream().map(product -> {
            ProductResponse prodResp = mapToProductResponse(product);

            // Map batch details for the current product
            List<BatchDetails> batchQuantities = batchDetailsMap.getOrDefault(product.getSku(), List.of())
                    .stream()
                    .map(this::mapToBatchDetails)
                    .toList();

            prodResp.setBatchQuantities(batchQuantities);
            return prodResp;
        }).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
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

    private BatchDetails mapToBatchDetails(Batch batch) {
        return new BatchDetails(
                batch.getBatchNumber(),
                batch.getQty(),
                batch.getUnitCost(),
                batch.getRetailPrice(),
                batch.getWholesalePrice()
        );
    }

    @Override
    public List<ProductResponse> getProductsByName(String prodName) {
        return productRepository.findAllByProductName(prodName).stream().map(product -> {
            ProductResponse prodResp = new ProductResponse();
            prodResp.setProductId(product.getProductId());
            prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
            prodResp.setProductName(product.getProductName());
            prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
            prodResp.setBarCode(product.getBarcode());
            prodResp.setSku(product.getSku());
            prodResp.setBarCodeType(product.getBarCodeType());
            prodResp.setProductType(product.getProductType());
            prodResp.setProductStatus(product.getProductStatus());
            prodResp.setDescription(product.getDescription());
            prodResp.setStockManagementEnable(product.getStockManagementEnable());
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
        prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
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
        prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
        prodResp.setLowQty(product.getLowQty());
        prodResp.setExpDateAvailable(product.getExpDateAvailable());
        prodResp.setExpDate(product.getExpDate());
        prodResp.setTaxGroup(product.getTaxGroup());
        prodResp.setTaxType(product.getTaxType());
        prodResp.setImgUrl(product.getImgUrl());
        prodResp.setBatchNo(product.getBatchNo());
        prodResp.setStatus(product.getStatus());
        prodResp.setBrandName(product.getBrandName());
        prodResp.setPartNumber(product.getPartNumber());
        prodResp.setVehicleList(new ArrayList<>(product.getVehicles()));
        return prodResp;
    }

    @Override
    public ProductResponse getProductById(String barcode) {
        Product product = productRepository.findByBarcode(barcode).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
        );
        ProductResponse prodResp = new ProductResponse();
        prodResp.setProductId(product.getProductId());
        prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
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
        prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
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
        prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
        prodResp.setProductName(product.getProductName());
        prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
        prodResp.setBarCode(product.getBarcode());
        prodResp.setSku(product.getSku());
        prodResp.setBarCodeType(product.getBarCodeType());
        prodResp.setProductType(product.getProductType());
        prodResp.setProductStatus(product.getProductStatus());
        prodResp.setDescription(product.getDescription());
        prodResp.setStockManagementEnable(product.getStockManagementEnable());
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

    private Product getSaveProduct(ProductRequest productRequest) {
        // Retrieve the currently authenticated user's username
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the Category object using catId
        Integer catId = productRequest.getCatId();
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.CATEGORY_NOT_FOUND));


        Product saveProduct = new Product();
        saveProduct.setCategory(category);
        saveProduct.setVehicles(new HashSet<>(productRequest.getVehicleList()));
        saveProduct.setBrandName(productRequest.getBrandName());
        saveProduct.setPartNumber(productRequest.getPartNumber());
        saveProduct.setProductName(productRequest.getProductName());
        saveProduct.setBarCodeAvailable(productRequest.getBarCodeAvailable());
        saveProduct.setBarcode(productRequest.getBarCode());
        saveProduct.setSku(productRequest.getSku());
        saveProduct.setBarCodeType(productRequest.getBarCodeType());
        saveProduct.setProductType(productRequest.getProductType());
        saveProduct.setProductStatus(productRequest.getProductStatus());
        saveProduct.setDescription(productRequest.getDescription());
        saveProduct.setStockManagementEnable(productRequest.getStockManagementEnable());
        saveProduct.setLowQty(productRequest.getLowQty());
        saveProduct.setExpDateAvailable(productRequest.getExpDateAvailable());
        saveProduct.setExpDate(productRequest.getExpDate());
        saveProduct.setTaxGroup(productRequest.getTaxGroup());
        saveProduct.setTaxType(productRequest.getTaxType());
        saveProduct.setImgUrl(productRequest.getImgUrl());
        saveProduct.setBatchNo(productRequest.getBatchNo());
        saveProduct.setStatus(productRequest.getStatus());
        saveProduct.setStatus(1);
        saveProduct.fillNew(currentUser);
        return saveProduct;
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        ProductResponse updatedProductResponse = new ProductResponse();

        // Retrieve the currently authenticated user's username
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch the Category object using catId
        Integer catId = productRequest.getCatId();
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.CATEGORY_NOT_FOUND));

        if (productRequest.getProductId() != null) {
            Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND)
            );
            product.setCategory(category);
            product.setProductName(productRequest.getProductName());
            product.setBarCodeAvailable(productRequest.getBarCodeAvailable());
            product.setBarcode(productRequest.getBarCode());
            product.setSku(productRequest.getSku());
            product.setBarCodeType(productRequest.getBarCodeType());
            product.setProductType(productRequest.getProductType());
            product.setProductStatus(productRequest.getProductStatus());
            product.setDescription(productRequest.getDescription());
            product.setStockManagementEnable(productRequest.getStockManagementEnable());
            product.setLowQty(productRequest.getLowQty());
            product.setExpDateAvailable(productRequest.getExpDateAvailable());
            product.setExpDate(productRequest.getExpDate());
            product.setTaxGroup(productRequest.getTaxGroup());
            product.setTaxType(productRequest.getTaxType());
            product.setImgUrl(productRequest.getImgUrl());
            product.setBatchNo(productRequest.getBatchNo());
            product.setBrandName(productRequest.getBrandName());
            product.setPartNumber(productRequest.getPartNumber());
            product.setVehicles(new HashSet<>(productRequest.getVehicleList()));
            product.fillUpdated(currentUser);
            Product updateProduct = productRepository.save(product);

            updatedProductResponse.setProductId(updateProduct.getProductId());
            updatedProductResponse.setCategory(categoryMapper.toCategoryResponse(updateProduct.getCategory()));
            updatedProductResponse.setProductName(updateProduct.getProductName());
            updatedProductResponse.setBarCodeAvailable(updateProduct.getBarCodeAvailable());
            updatedProductResponse.setBarCode(updateProduct.getBarcode());
            updatedProductResponse.setSku(updateProduct.getSku());
            updatedProductResponse.setBarCodeType(updateProduct.getBarCodeType());
            updatedProductResponse.setProductType(updateProduct.getProductType());
            updatedProductResponse.setProductStatus(updateProduct.getProductStatus());
            updatedProductResponse.setDescription(updateProduct.getDescription());
            updatedProductResponse.setStockManagementEnable(updateProduct.getStockManagementEnable());
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
            batchDetails.setRetailPrice(batch.getRetailPrice());
            batchDetails.setWholesalePrice(batch.getWholesalePrice());
            batchDetails.setQty(batch.getQty());

            return batchDetails;
        }).toList();
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String categoryId) {
        List<ProductResponse> productList = productRepository.findAllByCategory_catId(Integer.parseInt(categoryId)).stream().map(product -> {
            ProductResponse prodResp = new ProductResponse();
            prodResp.setProductId(product.getProductId());
            prodResp.setCategory(categoryMapper.toCategoryResponse(product.getCategory()));
            prodResp.setProductName(product.getProductName());
            prodResp.setBarCodeAvailable(product.getBarCodeAvailable());
            prodResp.setBarCode(product.getBarcode());
            prodResp.setSku(product.getSku());
            prodResp.setBarCodeType(product.getBarCodeType());
            prodResp.setProductType(product.getProductType());
            prodResp.setProductStatus(product.getProductStatus());
            prodResp.setDescription(product.getDescription());
            prodResp.setStockManagementEnable(product.getStockManagementEnable());
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

        return productList;
    }

    @Override
    public Double getAvailableQuantity(String skuId, String batchNumber) {
        Optional<Batch> bySkuAndBatchNumber = batchRepository.findByBatchNumberAndSku(batchNumber, skuId);
        if (bySkuAndBatchNumber.isEmpty()) {
            throw new ResourceNotFoundException(ErrorCodes.BATCH_NOT_FOUND);
        }
        return bySkuAndBatchNumber.get().getQty();
    }

    @Override
    public void deleteProduct(Integer prodId) {
        Optional<Product> productOptional = productRepository.findById(prodId);
        if (productOptional.isPresent()) {
            // Retrieve the currently authenticated user's username
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            Product product = productOptional.get();
            product.setStatus(0);
            product.fillUpdated(currentUser);
            productRepository.save(product);
        }
    }

    @Override
    public List<VehicleResponse> availableVehicles() {
        return vehicleRepository.findAll().stream().map(vehicleMapper::toVehicleResponse).toList();
    }

    @Override
    public VehicleResponse createVehicle(VehicleRequest request) {
        Optional<Vehicle> vehicleByMakeAndModel = vehicleRepository.findByMakeAndModel(request.getMake(), request.getModel());
        if (vehicleByMakeAndModel.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_VEHICLE);
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setName(request.getName());
        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toVehicleResponse(savedVehicle);
    }
}
