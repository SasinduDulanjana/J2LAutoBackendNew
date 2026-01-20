package com.example.smartPos.mapper;

import com.example.smartPos.controllers.requests.CategoryRequest;
import com.example.smartPos.controllers.responses.CategoryResponse;
import com.example.smartPos.repositories.model.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-14T20:16:47+0530",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toCategory(CategoryRequest categoryRequest) {
        if ( categoryRequest == null ) {
            return null;
        }

        Category category = new Category();

        category.setCatId( categoryRequest.getCatId() );
        category.setName( categoryRequest.getName() );
        category.setCatDesc( categoryRequest.getCatDesc() );
        category.setParent( categoryRequest.getParent() );

        return category;
    }

    @Override
    public CategoryRequest toCategoryRequest(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryRequest categoryRequest = new CategoryRequest();

        categoryRequest.setCatId( category.getCatId() );
        categoryRequest.setName( category.getName() );
        categoryRequest.setCatDesc( category.getCatDesc() );
        categoryRequest.setParent( category.getParent() );

        return categoryRequest;
    }

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setCatId( category.getCatId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setCatDesc( category.getCatDesc() );
        categoryResponse.setParent( category.getParent() );

        return categoryResponse;
    }
}
