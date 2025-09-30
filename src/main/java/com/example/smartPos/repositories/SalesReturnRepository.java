package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Sale;
import com.example.smartPos.repositories.model.SalesReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReturnRepository extends JpaRepository<SalesReturn, Integer> {
    List<SalesReturn> findBySale_SaleId(Integer saleId);

    List<SalesReturn> findByCustomerId(Integer customerId);

    @Query("SELECT sr FROM SalesReturn sr")
    List<SalesReturn> findAllWithCustomer();
}
