package com.example.order.controller;

import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Category;
import com.example.order.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category, @RequestParam String userEmail) throws OptionalEmpty {
        Category addedCategory = categoryService.addCategory(category, userEmail);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCategory(@RequestParam String categoryId, @RequestParam String userEmail) throws OptionalEmpty {
        Category category = categoryService.getCategoryById(categoryId);
        categoryService.deleteCategory(category, userEmail);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
    }
}
