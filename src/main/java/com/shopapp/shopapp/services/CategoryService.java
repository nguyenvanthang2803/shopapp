package com.shopapp.shopapp.services;

import com.shopapp.shopapp.dto.request.CategoryRequestDto;
import com.shopapp.shopapp.model.Category;
import com.shopapp.shopapp.repositories.CategoryRepo;
import com.shopapp.shopapp.services.impl.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepo categoryRepo;
    @Override
    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        Category newCategory = Category.builder().
                name(categoryRequestDto.getName()).
                build();
        return categoryRepo.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(()-> new RuntimeException("not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryRequestDto category) {
        Category existingCategory = getCategoryById(categoryId);
            existingCategory.setName(category.getName());
            categoryRepo.save(existingCategory);
                return existingCategory;
    }

    @Override
    public void deleteCategory(Long id) {
    categoryRepo.deleteById(id);
    }
}
