package com.java.expensetracker.controller;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.CategoryId;
import com.java.expensetracker.repository.CategoryRepository;
import com.java.expensetracker.request.CreateCategoryRequest;
import com.java.expensetracker.response.category.CategoryResponse;
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

    public CategoryResponse getCategoryConversionMethod(String username, String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findById(new CategoryId(username, categoryName));
        if(!optionalCategory.isPresent()) {
            return null;
        }
        Category category = optionalCategory.get();
        return new CategoryResponse(category.getUsername(), category.getCategoryName(), category.getCategoryBudget());
    }

    @GetMapping("{username}/category")
    public ResponseEntity<?> categories(@PathVariable String username) {
        Iterable<Category> categoryIterable = categoryRepository.findByUsername(username);

        if(((Collection<Category>) categoryIterable).size() == 0) {
            return new ResponseEntity<>(String.format
                    ("Username: %s not found. Send a valid request", username), HttpStatus.NOT_FOUND);
        }
        Collection<CategoryResponse> categories = new ArrayList<>();

        for(Category category : categoryIterable) {
            categories.add(new CategoryResponse(category.getUsername(), category.getCategoryName(), category.getCategoryBudget()));
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("{username}/category/{categoryName}")
    public ResponseEntity<?> getCategory(@PathVariable String username, @PathVariable String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findById(new CategoryId(username, categoryName));
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format
                    ("No entries found for username: %s and category: %s. Send a valid request",
                            username, categoryName), HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();
        return new ResponseEntity<>(new CategoryResponse(category.getUsername(), category.getCategoryName(),
                category.getCategoryBudget()), HttpStatus.OK);
    }

    @PostMapping("{username}/category")
    public ResponseEntity<?> createCategory(@PathVariable String username, @RequestBody CreateCategoryRequest createCategoryRequest) {
        String categoryName = createCategoryRequest.getCategoryName();
        BigDecimal categoryBudget = new BigDecimal(createCategoryRequest.getCategoryBudget());

        Optional<Category> optionalCategory = categoryRepository.findById(new CategoryId(username, categoryName));

        if(optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format
                    ("username: %s and category: %s already exists. Use update to update existing fields.",
                            username, categoryName), HttpStatus.CONFLICT);
        }

        Category result = categoryRepository.save(new Category(new CategoryId(username, categoryName), categoryBudget));

        CategoryResponse categoryResponse = new CategoryResponse(result.getUsername(),
                result.getCategoryName(), result.getCategoryBudget());
        String url = String.format("/api/%s/category", username + categoryName);
        url = encodeStringToUrl(url);

        try {
            return ResponseEntity.created(new URI(url)).body(categoryResponse);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the category", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("{username}/category/{categoryName}")
    public ResponseEntity<?> updateCategory(@PathVariable String username, @PathVariable String categoryName,
                                            @RequestParam String budget) throws URISyntaxException {
        Optional<Category> optionalCategory = categoryRepository.findById(new CategoryId(username, categoryName));

        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("No entries found for username: %s and category: %s. " +
                            "Send a valid request", username, categoryName), HttpStatus.NOT_FOUND);
        }

        Category category = optionalCategory.get();

        try {
            BigDecimal budgetBD = new BigDecimal(budget);
            category.setCategoryBudget(budgetBD);
        } catch (NumberFormatException exception) {
            return new ResponseEntity<>(String.format("Budget: %s not a valid number. Send a valid request", budget),
                    HttpStatus.CONFLICT);
        }


        Category result = categoryRepository.save(category);
        CategoryResponse categoryResponse = new CategoryResponse(result.getUsername(),
                result.getCategoryName(), result.getCategoryBudget());
        String url = String.format("/api/%s/category/%s", username, result.getCategoryName());
        url = encodeStringToUrl(url);
        return ResponseEntity.created(new URI(url)).body(categoryResponse);
    }

    @DeleteMapping("{username}/category/{categoryName}")
    public ResponseEntity<?> deleteCategory(@PathVariable String username, @PathVariable String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findById(new CategoryId(username, categoryName));
        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("No entries found for username: %s and category: %s. " +
                            "Send a valid request", username, categoryName), HttpStatus.NOT_FOUND);
        }

        try {
            categoryRepository.deleteById(new CategoryId(username, categoryName));
        } catch (DataIntegrityViolationException exception) {
            return new ResponseEntity<>("Category in use, Cannot be deleted", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(String.format("For username %s, category %s was deleted", username, categoryName),
                HttpStatus.OK);
    }

    private String encodeStringToUrl(String name) {
        return name.toString().replace(" ", "+");
    }
}
