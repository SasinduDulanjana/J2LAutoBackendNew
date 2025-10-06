package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.repositories.model.Expense;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-06T10:21:43+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ExpenseMapperImpl implements ExpenseMapper {

    @Override
    public ExpenseResponse toExpenseResponse(Expense expense) {
        if ( expense == null ) {
            return null;
        }

        ExpenseResponse expenseResponse = new ExpenseResponse();

        expenseResponse.setId( expense.getId() );
        expenseResponse.setDate( expense.getDate() );
        expenseResponse.setExpenseType( expense.getExpenseType() );
        expenseResponse.setDescription( expense.getDescription() );
        expenseResponse.setAmount( expense.getAmount() );
        expenseResponse.setReference( expense.getReference() );
        expenseResponse.setPaymentMethod( expense.getPaymentMethod() );
        expenseResponse.setBankName( expense.getBankName() );
        expenseResponse.setChequeDate( expense.getChequeDate() );
        expenseResponse.setChequeNumber( expense.getChequeNumber() );

        return expenseResponse;
    }
}
