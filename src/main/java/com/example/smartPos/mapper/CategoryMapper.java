package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.CategoryRequest;
import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CategoryResponse;
import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.repositories.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryRequest categoryRequest);

    CategoryRequest toCategoryRequest(Category category);

    CategoryResponse toCategoryResponse(Category category);
}
