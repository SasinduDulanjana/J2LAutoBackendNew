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

    List<SalesReturn> findBySale_SaleIdIn(List<Integer> saleIds);

    List<SalesReturn> findByCustomerId(Integer customerId);

    @Query("SELECT sr FROM SalesReturn sr")
    List<SalesReturn> findAllWithCustomer();

    @Query("SELECT FUNCTION('DATE_FORMAT', s.returnDate, '%Y-%m') AS month, SUM(CAST(s.refundAmount AS double)) " +
            "FROM SalesReturn s " +
            "GROUP BY FUNCTION('DATE_FORMAT', s.returnDate, '%Y-%m')")
    List<Object[]> findMonthlySaleReturns();

    @Query("SELECT SUM(CAST(s.refundAmount AS double)) FROM SalesReturn s")
    Double findTotalSalesReturns();
}
