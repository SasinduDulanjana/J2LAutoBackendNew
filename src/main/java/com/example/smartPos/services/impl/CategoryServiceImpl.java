package com.example.smartPos.services.impl;

import com.example.smartPos.controllers.requests.CategoryRequest;
import com.example.smartPos.controllers.responses.CategoryResponse;
import com.example.smartPos.exception.AlreadyExistsException;
import com.example.smartPos.exception.ResourceNotFoundException;
import com.example.smartPos.repositories.CategoryRepository;
import com.example.smartPos.repositories.model.Category;
import com.example.smartPos.services.ICategoryService;
import com.example.smartPos.util.CategoryConstants;
import com.example.smartPos.util.CustomerConstants;
import com.example.smartPos.util.ErrorCodes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll().stream().map(category -> {
            CategoryResponse catResp = new CategoryResponse();
            catResp.setCatId(category.getCatId());
            catResp.setName(category.getName());
            catResp.setCatDesc(category.getCatDesc());
            catResp.setParent(category.getParent());
            return catResp;
        }).toList();

    }

    @Override
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CATEGORY_NOT_FOUND)
        );
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCatId(category.getCatId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCatDesc(category.getCatDesc());
        categoryResponse.setParent(category.getParent());
        return categoryResponse;
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(ErrorCodes.CATEGORY_NOT_FOUND)
        );
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCatId(category.getCatId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCatDesc(category.getCatDesc());
        categoryResponse.setParent(category.getParent());
        return categoryResponse;
    }
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findByName(categoryRequest.getName());
        if (category.isPresent()) {
            throw new AlreadyExistsException(ErrorCodes.ALREADY_EXISTS_CATEGORY);
        }
        Category saveCategory = new Category();
        saveCategory.setName(categoryRequest.getName());
        saveCategory.setCatDesc(categoryRequest.getCatDesc());
        saveCategory.setParent(categoryRequest.getParent());
        saveCategory.setStatus(1);
        saveCategory.fillNew("ADMIN USER");
        Category savedCategory = categoryRepository.save(saveCategory);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName(savedCategory.getName());
        categoryResponse.setStatusCode(CategoryConstants.STATUS_201);
        categoryResponse.setDesc(CategoryConstants.MESSAGE_201);
        return categoryResponse;
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest) {
        CategoryResponse updatedCategoryResponse = new CategoryResponse();
        if (categoryRequest.getCatId() != null) {
            Category category = categoryRepository.findById(categoryRequest.getCatId()).orElseThrow(
                    () -> new ResourceNotFoundException(ErrorCodes.CATEGORY_NOT_FOUND)
            );
            category.setName(categoryRequest.getName());
            category.setCatDesc(categoryRequest.getCatDesc());
            category.setParent(categoryRequest.getParent());
            category.fillUpdated("ADMIN USER");
            Category updatedCategory = categoryRepository.save(category);

            updatedCategoryResponse.setCatId(updatedCategory.getCatId());
            updatedCategoryResponse.setName(updatedCategory.getName());
            updatedCategoryResponse.setCatDesc(updatedCategory.getCatDesc());
            updatedCategoryResponse.setParent(updatedCategory.getParent());
        }
        return updatedCategoryResponse;
    }
}
