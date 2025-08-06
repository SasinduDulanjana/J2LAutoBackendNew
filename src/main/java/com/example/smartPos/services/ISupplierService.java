package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.SupplierRequest;
import com.example.smartPos.controllers.responses.SupplierResponse;

import java.util.List;

public interface ISupplierService {
    List<SupplierResponse> getAllSuppliers();

    SupplierResponse getSupplierByPhone(String mobileNo);

    SupplierResponse getSupplierById(Integer id);

    SupplierResponse createSupplier(SupplierRequest supplierRequest);

    SupplierResponse updateSupplier(SupplierRequest supplierRequest);

    void deletSupplier(Integer supId);
}
