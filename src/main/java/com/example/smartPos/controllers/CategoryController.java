package com.example.smartPos.controllers;

import com.example.smartPos.controllers.requests.CategoryRequest;
import com.example.smartPos.controllers.requests.ProductRequest;
import com.example.smartPos.controllers.responses.CategoryResponse;
import com.example.smartPos.controllers.responses.CommonResponse;
import com.example.smartPos.services.ICategoryService;
import com.example.smartPos.services.ICustomerService;
import com.example.smartPos.util.ResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/api/getAllCategories")
    public List<CategoryResponse> getAllCustomers() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/api/getCategoryByName/{name}")
    public ResponseEntity<CommonResponse> getCategoryByName(@PathVariable String name) {
        CategoryResponse categoryResponse = categoryService.getCategoryByName(name);
        return ResponseCreator.success(categoryResponse);
    }

    @GetMapping(path = "/api/getCategoryById/{id}")
    public ResponseEntity<CommonResponse> getCategoryByName(@PathVariable Integer id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseCreator.success(categoryResponse);
    }

    @PostMapping(path = "/api/createCategory")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse savedCategory = categoryService.createCategory(categoryRequest);
        return ResponseCreator.success(savedCategory);
    }

    @PostMapping(path = "/api/updateCategory")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryRequest);
        return ResponseCreator.success(updatedCategory);
    }

    @PostMapping(path = "/api/deleteCategory")
    public void deleteCategory(@RequestBody CategoryRequest request) {
        categoryService.deleteCategory(request.getCatId());
        System.out.println("OK");
    }

}
