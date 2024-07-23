package com.example.smartPos.services;

import com.example.smartPos.controllers.requests.CategoryRequest;
import com.example.smartPos.controllers.requests.CustomerRequest;
import com.example.smartPos.controllers.responses.CategoryResponse;
import com.example.smartPos.controllers.responses.CustomerResponse;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryByName(String name);

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(CategoryRequest categoryRequest);
}
