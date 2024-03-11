package com.shopapp.shopapp.services.impl;

import com.shopapp.shopapp.dto.request.CategoryRequestDto;
import com.shopapp.shopapp.model.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryRequestDto categoryRequestDto);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId,CategoryRequestDto categoryRequestDto);
    void deleteCategory(Long id);
}
