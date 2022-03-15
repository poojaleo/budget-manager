package com.java.expensetracker.controller;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.repository.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/category")
    public ResponseEntity<Collection<Category>> categories() {
        Collection<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category) throws URISyntaxException {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());

        if(optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s is already in use. Send a valid request", category.getId()), HttpStatus.CONFLICT);
        }

        Category result = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestParam String categoryName) throws URISyntaxException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();
        category.setCategoryName(categoryName);
        Category result = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>("Category in use, Cannot be deleted", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
