package com.shopapp.shopapp.controller;

import com.shopapp.shopapp.dto.request.CategoryRequestDto;
import com.shopapp.shopapp.model.Category;
import com.shopapp.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final CategoryService categoryService;
    @PostMapping()
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto
            , BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessage =result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok(categoryRequestDto.getName());
    }
    @GetMapping()
    public ResponseEntity<List<Category>> getAllcategory(){
        List<Category> listCategory=categoryService.getAllCategories();
        return ResponseEntity.ok(listCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id
    ,@RequestBody CategoryRequestDto categoryRequestDto){
        categoryService.updateCategory(id,categoryRequestDto);
        return ResponseEntity.ok("update id="+id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);

        return ResponseEntity.ok("delete id="+id);
    }
}
