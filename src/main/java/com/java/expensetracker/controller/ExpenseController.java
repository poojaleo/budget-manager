package com.java.expensetracker.controller;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.CategoryId;
import com.java.expensetracker.model.Expense;
import com.java.expensetracker.model.ExpenseKey;
import com.java.expensetracker.repository.CategoryRepository;
import com.java.expensetracker.repository.ExpenseRepository;
import com.java.expensetracker.request.CreateExpenseRequest;
import com.java.expensetracker.request.UpdateExpenseRequest;
import com.java.expensetracker.response.expense.ExpenseResponse;
import com.java.expensetracker.utility.ExpenseTrackerUtility;
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
                    createExpenseRequest.getCategoryName()), HttpStatus.BAD_REQUEST);
        }

        String expenseId = ExpenseTrackerUtility.generateId();

        Expense expense = new Expense(new ExpenseKey(username, expenseId), createExpenseRequest.getExpenseDate(),
                createExpenseRequest.getDescription(), createExpenseRequest.getAmount(),
                createExpenseRequest.getCategoryName(), createExpenseRequest.getMerchant());

        Expense result = expenseRepository.save(expense);

        return returnExpenseResponse(result);
    }

    @PutMapping("{username}/expense/{expenseId}")
    public ResponseEntity<?> updateExpense(@PathVariable String username, @PathVariable String expenseId,
                                           @RequestBody UpdateExpenseRequest updateExpenseRequest) {
        if(updateExpenseRequest.getCategoryName() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById
                    (new CategoryId(username, updateExpenseRequest.getCategoryName()));

            if(!optionalCategory.isPresent()) {
                return new ResponseEntity<>(String.format("Category %s does not exist. Please create a category first. ",
                        updateExpenseRequest.getCategoryName()), HttpStatus.BAD_REQUEST);
            }
        }


        Optional<Expense> optionalExpense = expenseRepository.findById(new ExpenseKey(username, expenseId));

        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("No entries found for username: %s and expenseId: %s. " +
                    "Send a valid request", username, expenseId), HttpStatus.NOT_FOUND);
        }

        Expense expense = optionalExpense.get();

        if(updateExpenseRequest.getExpenseDate() != null &&
                !(updateExpenseRequest.getExpenseDate().equals(expense.getExpenseDate()))) {
            if(updateExpenseRequest.getExpenseDate().isEmpty()) {
                return new ResponseEntity<>("Date field cannot be empty", HttpStatus.BAD_REQUEST);
            }
            expense.setExpenseDate(updateExpenseRequest.getExpenseDate());
        }

        if(updateExpenseRequest.getDescription() != null &&
                !(updateExpenseRequest.getDescription().equals(expense.getDescription()))) {
            expense.setDescription(updateExpenseRequest.getDescription());
        }

        if(updateExpenseRequest.getAmount() != null &&
                !(updateExpenseRequest.getAmount().equals(expense.getAmount()))) {
            if(updateExpenseRequest.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                return new ResponseEntity<>("Expense cannot be zero", HttpStatus.BAD_REQUEST);
            }
            expense.setAmount(updateExpenseRequest.getAmount());
        }

        if(updateExpenseRequest.getCategoryName() != null &&
                !(updateExpenseRequest.getCategoryName().equals(expense.getCategoryName()))) {
            expense.setCategoryName(updateExpenseRequest.getCategoryName());
        }

        if(updateExpenseRequest.getMerchant() != null &&
                !(updateExpenseRequest.getMerchant().equals(expense.getMerchant()))) {
            expense.setMerchant(updateExpenseRequest.getMerchant());
        }

        Expense result = expenseRepository.save(expense);
        return returnExpenseResponse(result);

    }

    @DeleteMapping("{username}/expense/{expenseId}")
    public ResponseEntity<?> deleteExpense(@PathVariable String username, @PathVariable String expenseId) {
        Optional<Expense> optionalExpense = this.expenseRepository.findById(new ExpenseKey(username, expenseId));

        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("No entries found for username: %s and expenseId: %s. " +
                    "Send a valid request",username, expenseId), HttpStatus.NOT_FOUND);
        }

        expenseRepository.deleteById(new ExpenseKey(username, expenseId));
        return new ResponseEntity<>(String.format("Expense for user: %s and Expense id: %s was deleted",
                username, expenseId), HttpStatus.OK);
    }

    private ResponseEntity<?> returnExpenseResponse(Expense result) {
        ExpenseResponse expenseResponse = new ExpenseResponse(result.getUsername(), result.getExpenseId(),
                result.getExpenseDate(), result.getDescription(), result.getAmount(),
                result.getCategoryName(), result.getMerchant());

        String url = String.format("/api/%s/expense", result.getUsername() + result.getExpenseId());
        url = encodeStringToUrl(url);

        try {
            return ResponseEntity.created(new URI(url)).body(expenseResponse);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String encodeStringToUrl(String name) {
        return name.replace(" ", "+");
    }

}
