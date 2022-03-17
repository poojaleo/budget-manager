package com.java.expensetracker.controller;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> getCategoryConversionMethod(String categoryName) {
        return categoryRepository.findById(categoryName);
    }

    @GetMapping("/category")
    public ResponseEntity<Collection<Category>> categories() {
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        Collection<Category> categories = new ArrayList<>();

        for(Category category : categoryIterable) {
            categories.add(category);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getCategory(@PathVariable String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryName);
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format
                    ("Category Name: %s not found. Send a valid request", categoryName), HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getCategoryName());

        if(optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format
                    ("Category: %s already exists. Send a valid request", category.getCategoryName()), HttpStatus.CONFLICT);
        }

        Category result = categoryRepository.save(category);
        try {
            return ResponseEntity.created(new URI("/api/category" + category.getCategoryName())).body(result);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the category", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/category/{categoryName}")
    public ResponseEntity<?> updateCategory(@PathVariable String categoryName, @RequestParam String budget) throws URISyntaxException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryName);

        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Category: %s not found. Send a valid request", categoryName), HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();

        try {
            BigDecimal budgetBD = new BigDecimal(budget);
            category.setCategoryBudget(budgetBD);
        } catch (NumberFormatException exception) {
            return new ResponseEntity<>(String.format("Budget: %s not a valid number. Send a valid request", budget), HttpStatus.CONFLICT);
        }


        Category result = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/category" + result.getCategoryName())).body(result);
    }

    @DeleteMapping("/category/{categoryName}")
    public ResponseEntity<?> deleteCategory(@PathVariable String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryName);
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Category: %s not found. Send a valid request", categoryName), HttpStatus.NOT_FOUND);
        }

        try {
            categoryRepository.deleteById(categoryName);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>("Category in use, Cannot be deleted", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
