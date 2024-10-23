package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    List<Sale> findAllBySaleDate(String saleDate);

    List<Sale> findAllByCustId(Integer custId);

    List<Sale> findAllByUserId(Integer userId);

    Optional<Sale> findByInvoiceNumber(String invoiceNumber);

    Sale findTopByOrderBySaleIdDesc();

}
