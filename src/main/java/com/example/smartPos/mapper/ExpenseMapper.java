package com.example.smartPos.mapper;

import com.example.smartPos.controllers.responses.ExpenseResponse;
import com.example.smartPos.repositories.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    ExpenseMapper INSTANCE = Mappers.getMapper(ExpenseMapper.class);

    ExpenseResponse toExpenseResponse(Expense expense);
}
