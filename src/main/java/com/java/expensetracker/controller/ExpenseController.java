package com.java.expensetracker.controller;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.CategoryId;
import com.java.expensetracker.model.Expense;
import com.java.expensetracker.repository.CategoryRepository;
import com.java.expensetracker.repository.ExpenseRepository;
import com.java.expensetracker.request.CreateExpenseRequest;
import com.java.expensetracker.utility.ExpenseTrackerUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/expense")
    public ResponseEntity<Collection<Expense>> getAllExpenses() {
        Iterable<Expense> expenseIterator = expenseRepository.findAll();
        Collection<Expense> expenses = new ArrayList<>();

        for(Expense expense : expenseIterator) {
            Optional<Category> optionalCategory = categoryRepository.findById(
                    new CategoryId(expense.getCategory().getUsername(), expense.getCategory().getCategoryName()));
            if(optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                expense.setCategory(category);
            }
            expenses.add(expense);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/expense/{expenseId}")
    public ResponseEntity<?> getExpense(@PathVariable String expenseId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);

        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", expenseId), HttpStatus.NOT_FOUND);
        }

        Expense expense = optionalExpense.get();
        Optional<Category> optionalCategory = categoryRepository.findById(
                new CategoryId(expense.getCategory().getUsername(), expense.getCategory().getCategoryName())
        );
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            expense.setCategory(category);
        }
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        Optional<Expense> optionalExpense = this.expenseRepository.findById(id);
        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        expenseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/expense")
    public ResponseEntity<?> createExpense(@RequestBody CreateExpenseRequest expenseRequest) {

        String id = ExpenseTrackerUtility.generateId();
        Expense expense = new Expense(id, expenseRequest);

        Expense result = expenseRepository.save(expense);
        try {
            return ResponseEntity.created(new URI("/api/expense/" + expense.getExpenseId())).body(result);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
