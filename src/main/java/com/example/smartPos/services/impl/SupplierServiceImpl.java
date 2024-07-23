package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.SupplierRequest;
import com.example.smartPos.controllers.responses.SupplierResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.SupplierRepository;
import com.example.smartPos.repositories.model.Supplier;
import com.example.smartPos.services.ISupplierService;
import com.example.smartPos.util.CustomerConstants;
import com.example.smartPos.util.ErrorCodes;
import com.example.smartPos.util.SupplierConstants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements ISupplierService {

    final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }


    @Override
    public List<SupplierResponse> getAllSuppliers() {

        return supplierRepository.findAll().stream().map(supplier -> {
            SupplierResponse supResp = new SupplierResponse();
            supResp.setSupId(supplier.getSupId());
            supResp.setPhone(supplier.getPhone());
            supResp.setName(supplier.getName());
            supResp.setAddress(supplier.getAddress());
            supResp.setEmail(supplier.getEmail());
            supResp.setStatus(supplier.getStatus());
            return supResp;
        }).toList();

    }

    @Override
    public SupplierResponse getSupplierByPhone(String mobileNo) {
        Supplier supplier = supplierRepository.findByPhone(mobileNo).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
        );
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setSupId(supplier.getSupId());
        supplierResponse.setPhone(supplier.getPhone());
        supplierResponse.setName(supplier.getName());
        supplierResponse.setAddress(supplier.getAddress());
        supplierResponse.setEmail(supplier.getEmail());
        return supplierResponse;
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
        );
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setSupId(supplier.getSupId());
        supplierResponse.setPhone(supplier.getPhone());
        supplierResponse.setName(supplier.getName());
        supplierResponse.setAddress(supplier.getAddress());
        supplierResponse.setEmail(supplier.getEmail());
        return supplierResponse;
    }

    @Override
    public SupplierResponse createSupplier(SupplierRequest supplierRequest) {
        Optional<Supplier> supplier = supplierRepository.findByPhone(supplierRequest.getPhone());
        if (supplier.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_CUSTOMER);
        }
        Supplier saveSupplier = new Supplier();
        saveSupplier.setName(supplierRequest.getName());
        saveSupplier.setAddress(supplierRequest.getAddress());
        saveSupplier.setPhone(supplierRequest.getPhone());
        saveSupplier.setEmail(supplierRequest.getEmail());
        saveSupplier.setStatus(1);
        saveSupplier.fillNew("ADMIN USER");
        Supplier savedSupplier = supplierRepository.save(saveSupplier);

        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setPhone(savedSupplier.getPhone());
        supplierResponse.setStatusCode(SupplierConstants.STATUS_201);
        supplierResponse.setDesc(SupplierConstants.MESSAGE_201);
        return supplierResponse;
    }

    @Override
    public SupplierResponse updateSupplier(SupplierRequest supplierRequest) {
        SupplierResponse updatedSupplierResponse = new SupplierResponse();
        if (supplierRequest.getSupId() != null) {
            Supplier supplier = supplierRepository.findById(supplierRequest.getSupId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.SUPPLIER_NOT_FOUND)
            );
            supplier.setPhone(supplierRequest.getPhone());
            supplier.setName(supplierRequest.getName());
            supplier.setAddress(supplierRequest.getAddress());
            supplier.setEmail(supplierRequest.getEmail());
            supplier.fillUpdated("ADMIN USER");
            Supplier updatedSupplier = supplierRepository.save(supplier);

            updatedSupplierResponse.setSupId(updatedSupplier.getSupId());
            updatedSupplierResponse.setPhone(updatedSupplier.getPhone());
            updatedSupplierResponse.setName(updatedSupplier.getName());
            updatedSupplierResponse.setAddress(updatedSupplier.getAddress());
            updatedSupplierResponse.setEmail(updatedSupplier.getEmail());
        }
        return updatedSupplierResponse;
    }
}
