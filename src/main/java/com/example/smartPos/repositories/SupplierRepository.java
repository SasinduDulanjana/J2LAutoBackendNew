package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findByPhone(String supplierId);

    List<Supplier> findAllByStatus(Integer supId);

}
