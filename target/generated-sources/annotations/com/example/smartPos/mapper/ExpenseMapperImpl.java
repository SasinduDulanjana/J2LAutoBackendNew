package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.repositories.model.Expense;
import java.text.SimpleDateFormat;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-14T20:16:47+0530",
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
        if ( expense.getDate() != null ) {
            expenseResponse.setDate( new SimpleDateFormat().format( expense.getDate() ) );
        }
        expenseResponse.setExpenseType( expense.getExpenseType() );
        expenseResponse.setDescription( expense.getDescription() );
        expenseResponse.setAmount( expense.getAmount() );
        expenseResponse.setReference( expense.getReference() );

        return expenseResponse;
    }
}
