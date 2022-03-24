package com.java.expensetracker.controller;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.CategoryId;
import com.java.expensetracker.model.Expense;
import com.java.expensetracker.model.ExpenseKey;
import com.java.expensetracker.repository.CategoryRepository;
import com.java.expensetracker.repository.ExpenseRepository;
import com.java.expensetracker.request.CreateExpenseRequest;
import com.java.expensetracker.response.expense.ExpenseResponse;
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

    @GetMapping("{username}/expense")
    public ResponseEntity<?> getAllExpenses(@PathVariable String username) {
        Iterable<Expense> expenseIterator = expenseRepository.findByUsername(username);

        if(((Collection<Expense>) expenseIterator).size() == 0) {
            return new ResponseEntity<>(String.format
                    ("Username: %s not found. Send a valid request", username), HttpStatus.NOT_FOUND);
        }
        Collection<ExpenseResponse> expenses = new ArrayList<>();

        for(Expense expense : expenseIterator) {

            expenses.add(new ExpenseResponse(expense.getUsername(), expense.getExpenseId(), expense.getExpenseDate(),
                    expense.getDescription(), expense.getAmount(), expense.getCategoryName(), expense.getMerchant()));
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("{username}/expense/{expenseId}")
    public ResponseEntity<?> getSpecificExpense(@PathVariable String username, @PathVariable String expenseId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(new ExpenseKey(username, expenseId));

        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("No entries found for username: %s and expenseId: %s. " +
                    "Send a valid request",username, expenseId), HttpStatus.NOT_FOUND);
        }

        Expense expense = optionalExpense.get();

        return new ResponseEntity<ExpenseResponse>(new ExpenseResponse(expense.getUsername(), expense.getExpenseId(),
                expense.getExpenseDate(), expense.getDescription(), expense.getAmount(), expense.getCategoryName(),
                expense.getMerchant()), HttpStatus.OK);
    }

    @PostMapping("{username}/expense")
    public ResponseEntity<?> createExpense(@PathVariable String username,
                                           @RequestBody CreateExpenseRequest createExpenseRequest) {

        Optional<Category> optionalCategory = categoryRepository.findById
                (new CategoryId(username, createExpenseRequest.getCategoryName()));

        if(!optionalCategory.isPresent()) {
            return new ResponseEntity<>(String.format("Category %s does not exist. Please create a category first. ",
                    createExpenseRequest.getCategoryName()), HttpStatus.NOT_FOUND);
        }

        String expenseId = ExpenseTrackerUtility.generateId();

        Expense expense = new Expense(new ExpenseKey(username, expenseId), createExpenseRequest.getExpenseDate(),
                createExpenseRequest.getDescription(), createExpenseRequest.getAmount(),
                createExpenseRequest.getCategoryName(), createExpenseRequest.getMerchant());

        Expense result = expenseRepository.save(expense);

        ExpenseResponse expenseResponse = new ExpenseResponse(result.getUsername(), result.getExpenseId(),
                result.getExpenseDate(), result.getDescription(), result.getAmount(),
                result.getCategoryName(), result.getMerchant());

        String url = String.format("/api/%s/expense", username + result.getExpenseId());
        url = encodeStringToUrl(url);

        try {
            return ResponseEntity.created(new URI(url)).body(expenseResponse);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@DeleteMapping("{username}/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable String id) {
        *//*Optional<Expense> optionalExpense = this.expenseRepository.findById(id);
        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        expenseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);*//*
    }*/

    private String encodeStringToUrl(String name) {
        return name.toString().replace(" ", "+");
    }




}
