package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.requests.SupplierRequest;
import com.example.smartPos.controllers.responses.CommonResponse;
import com.example.smartPos.controllers.responses.SupplierResponse;
import com.example.smartPos.services.ISupplierService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    ISupplierService supplierService;

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping(path = "/api/getAllSuppliers")
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping(path = "/api/getSupplierByPhone/{mobileNo}")
    public ResponseEntity<CommonResponse> getSupplierByPhone(@PathVariable String mobileNo) {
        SupplierResponse supplierResponse = supplierService.getSupplierByPhone(mobileNo);
        return ResponseCreator.success(supplierResponse);
    }

    @GetMapping(path = "/api/getSupplierById/{id}")
    public ResponseEntity<CommonResponse> getSupplierById(@PathVariable Integer id) {
        SupplierResponse supplierResponse = supplierService.getSupplierById(id);
        return ResponseCreator.success(supplierResponse);
    }

    @PostMapping(path = "/api/createSupplier")
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody SupplierRequest supplierRequest) {
        SupplierResponse savedSupplier = supplierService.createSupplier(supplierRequest);
        return ResponseCreator.success(savedSupplier);
    }

    @PostMapping(path = "/api/updateSupplier")
    public ResponseEntity<SupplierResponse> updateSupplier(@RequestBody SupplierRequest supplierRequest) {
        SupplierResponse updatedSupplier = supplierService.updateSupplier(supplierRequest);
        return ResponseCreator.success(updatedSupplier);
    }

    @PostMapping(path = "/api/deleteSupplier")
    public void deletedSupplier(@RequestBody SupplierRequest request) {
        supplierService.deletSupplier(request.getSupId());
        System.out.println("OK");
    }
}
