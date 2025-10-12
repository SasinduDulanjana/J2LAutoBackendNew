package com.example.smartPos.repositories;

import com.example.smartPos.repositories.model.Expense;
import com.example.smartPos.repositories.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Query("SELECT FUNCTION('DATE_FORMAT', e.date, '%Y-%m') AS month, SUM(e.amount) " +
            "FROM Expense e " +
            "GROUP BY FUNCTION('DATE_FORMAT', e.date, '%Y-%m') " +
            "ORDER BY month ASC")
    List<Object[]> findMonthlyTotalExpenses();

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    Double findTotalExpenses();

    @Query("SELECT pd FROM PaymentDetails pd " +
            "JOIN pd.payment p " +
            "WHERE p.referenceId = :expenseId " +
            "AND p.referenceType = 'EXPENSE'" +
            "AND p.paymentType = 'PAYMENT'")
    List<PaymentDetails> findPaymentDetailsByExpenseId(@Param("expenseId") Integer expenseId);

}
