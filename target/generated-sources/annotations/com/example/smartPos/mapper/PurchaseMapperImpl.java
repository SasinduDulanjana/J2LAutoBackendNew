package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.requests.PurchaseRequest;
import com.example.smartPos.controllers.responses.CategoryResponse;
import com.example.smartPos.controllers.responses.ProductResponse;
import com.example.smartPos.controllers.responses.PurchaseResponse;
import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.repositories.model.Product;
import com.example.smartPos.repositories.model.Purchase;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-06T10:21:43+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Override
    public Purchase toPurchase(PurchaseRequest purchaseRequest) {
        if ( purchaseRequest == null ) {
            return null;
        }

        Purchase purchase = new Purchase();

        purchase.setPurchaseId( purchaseRequest.getPurchaseId() );
        purchase.setPurchaseName( purchaseRequest.getPurchaseName() );
        purchase.setInvoiceNumber( purchaseRequest.getInvoiceNumber() );
        purchase.setDeliveryTime( purchaseRequest.getDeliveryTime() );
        purchase.setInvoiceDate( purchaseRequest.getInvoiceDate() );
        purchase.setConnectionStatus( purchaseRequest.getConnectionStatus() );
        purchase.setPaymentStatus( purchaseRequest.getPaymentStatus() );
        purchase.setProductType( purchaseRequest.getProductType() );
        purchase.setProducts( productRequestListToProductList( purchaseRequest.getProducts() ) );
        purchase.setStatus( purchaseRequest.getStatus() );
        purchase.setTotalCost( purchaseRequest.getTotalCost() );
        purchase.setPaidAmount( purchaseRequest.getPaidAmount() );
        purchase.setFullyPaid( purchaseRequest.getFullyPaid() );
        purchase.setPaymentType( purchaseRequest.getPaymentType() );

        return purchase;
    }

    @Override
    public PurchaseResponse toPurchaseResponse(Purchase purchase) {
        if ( purchase == null ) {
            return null;
        }

        PurchaseResponse purchaseResponse = new PurchaseResponse();

        purchaseResponse.setPurchaseId( purchase.getPurchaseId() );
        purchaseResponse.setSupplierId( purchase.getSupplierId() );
        purchaseResponse.setPurchaseName( purchase.getPurchaseName() );
        purchaseResponse.setInvoiceNumber( purchase.getInvoiceNumber() );
        purchaseResponse.setDeliveryTime( purchase.getDeliveryTime() );
        purchaseResponse.setInvoiceDate( purchase.getInvoiceDate() );
        purchaseResponse.setConnectionStatus( purchase.getConnectionStatus() );
        purchaseResponse.setPaymentStatus( purchase.getPaymentStatus() );
        purchaseResponse.setProductType( purchase.getProductType() );
        purchaseResponse.setProducts( productListToProductResponseList( purchase.getProducts() ) );
        purchaseResponse.setStatus( purchase.getStatus() );
        purchaseResponse.setTotalCost( purchase.getTotalCost() );
        purchaseResponse.setPaidAmount( purchase.getPaidAmount() );
        purchaseResponse.setFullyPaid( purchase.getFullyPaid() );
        purchaseResponse.setPaymentType( purchase.getPaymentType() );
        purchaseResponse.setChequeNo( purchase.getChequeNo() );

        return purchaseResponse;
    }

    protected Product productRequestToProduct(ProductRequest productRequest) {
        if ( productRequest == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( productRequest.getProductId() );
        product.setProductName( productRequest.getProductName() );
        product.setBarCodeAvailable( productRequest.getBarCodeAvailable() );
        product.setSku( productRequest.getSku() );
        product.setBarCodeType( productRequest.getBarCodeType() );
        product.setProductType( productRequest.getProductType() );
        product.setProductStatus( productRequest.getProductStatus() );
        product.setDescription( productRequest.getDescription() );
        product.setStockManagementEnable( productRequest.getStockManagementEnable() );
        product.setLowQty( productRequest.getLowQty() );
        product.setExpDateAvailable( productRequest.getExpDateAvailable() );
        product.setExpDate( productRequest.getExpDate() );
        product.setTaxGroup( productRequest.getTaxGroup() );
        product.setTaxType( productRequest.getTaxType() );
        product.setImgUrl( productRequest.getImgUrl() );
        product.setBatchNo( productRequest.getBatchNo() );
        product.setStatus( productRequest.getStatus() );
        product.setRemainingQty( productRequest.getRemainingQty() );
        product.setBrandName( productRequest.getBrandName() );
        product.setPartNumber( productRequest.getPartNumber() );

        return product;
    }

    protected List<Product> productRequestListToProductList(List<ProductRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<Product> list1 = new ArrayList<Product>( list.size() );
        for ( ProductRequest productRequest : list ) {
            list1.add( productRequestToProduct( productRequest ) );
        }

        return list1;
    }

    protected CategoryResponse categoryToCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setCatId( category.getCatId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setCatDesc( category.getCatDesc() );
        categoryResponse.setParent( category.getParent() );

        return categoryResponse;
    }

    protected ProductResponse productToProductResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setProductId( product.getProductId() );
        productResponse.setCategory( categoryToCategoryResponse( product.getCategory() ) );
        productResponse.setProductName( product.getProductName() );
        productResponse.setBarCodeAvailable( product.getBarCodeAvailable() );
        productResponse.setSku( product.getSku() );
        productResponse.setBarCodeType( product.getBarCodeType() );
        productResponse.setProductType( product.getProductType() );
        productResponse.setProductStatus( product.getProductStatus() );
        productResponse.setDescription( product.getDescription() );
        productResponse.setStockManagementEnable( product.getStockManagementEnable() );
        productResponse.setLowQty( product.getLowQty() );
        productResponse.setExpDateAvailable( product.getExpDateAvailable() );
        productResponse.setExpDate( product.getExpDate() );
        productResponse.setTaxGroup( product.getTaxGroup() );
        productResponse.setTaxType( product.getTaxType() );
        productResponse.setImgUrl( product.getImgUrl() );
        productResponse.setBatchNo( product.getBatchNo() );
        productResponse.setStatus( product.getStatus() );
        productResponse.setBrandName( product.getBrandName() );
        productResponse.setPartNumber( product.getPartNumber() );

        return productResponse;
    }

    protected List<ProductResponse> productListToProductResponseList(List<Product> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductResponse> list1 = new ArrayList<ProductResponse>( list.size() );
        for ( Product product : list ) {
            list1.add( productToProductResponse( product ) );
        }

        return list1;
    }
}
