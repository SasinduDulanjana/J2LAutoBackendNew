package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.requests.SupplierRequest;
import com.example.smartPos.controllers.responses.*;
import com.example.smartPos.services.ISupplierService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@CrossOrigin
public class SupplierController {

    ISupplierService supplierService;

    public SupplierController(ISupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping(path = "/api/getAllSuppliers")
    public List<SupplierResponse> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping(path = "/api/getAllSuppliersWithoutStatus")
    public List<SupplierResponse> getAllSuppliersWithoutStatus() {
        return supplierService.getAllSuppliersWithoutStatus();
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

    @GetMapping("/api/getAllSupplierDetailsWithSummary")
    public List<SupplierResponse> getAllSupplierDetailsWithSummary() {
        return supplierService.getAllSupplierDetailsWithSummary();
    }

    @GetMapping("/api/getSupplierWithOutstanding/{supId}")
    public SupplierPaymentDetailsResponse getSupplierWithOutstanding(@PathVariable Integer supId) {
        return supplierService.getSupplierDetailsWithSummary(supId);
    }

    @GetMapping("/api/getSupplierOutstanding/{supplierId}")
    public List<SupplierOutstandingResponse> getSupplierOutstanding(@PathVariable Integer supplierId) {
        return supplierService.getSupplierOutstanding(supplierId);
    }

}
