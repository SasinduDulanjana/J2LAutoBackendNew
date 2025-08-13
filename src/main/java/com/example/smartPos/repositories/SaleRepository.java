package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

//    List<Sale> findAllBySaleDate(String saleDate);

    List<Sale> findAllByCustId(Integer custId);

    List<Sale> findAllByUserId(Integer userId);

    Optional<Sale> findByInvoiceNumber(String invoiceNumber);

    Sale findTopByOrderBySaleIdDesc();

    List<Sale> findAllByStatus(Integer status);

    List<Sale> findAllByIsHold(Boolean isHold);

    List<Sale> findAllByIsFullyPaidAndStatusNot(Boolean isFullyPaid, int status);

    List<Sale> findAllByIsHoldAndStatusNot(boolean isHold, int status);

    @Query("SELECT s FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate AND s.status != 0")
    List<Sale> findAllBySaleDateBetweenAndStatusNot(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
