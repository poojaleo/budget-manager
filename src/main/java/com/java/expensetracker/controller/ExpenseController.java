package com.java.expensetracker.controller;

import com.java.expensetracker.model.Expense;
import com.java.expensetracker.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expense")
    public ResponseEntity<Collection<Expense>> getAllExpenses() {
        return new ResponseEntity<>(expenseRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/expense/{id}")
    public ResponseEntity<?> getExpense(@PathVariable Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        Expense expense = optionalExpense.get();
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    @DeleteMapping("/expense/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        Optional<Expense> optionalExpense = this.expenseRepository.findById(id);
        if(!optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format("Id: %s not found. Send a valid request", id), HttpStatus.NOT_FOUND);
        }

        expenseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/expense")
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        Optional<Expense> optionalExpense = this.expenseRepository.findById(expense.getId());

        if(optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format(
                    "Id: %s is already in use. Send a valid request", expense.getId()), HttpStatus.CONFLICT);
        }

        Expense result = expenseRepository.save(expense);
        try {
            return ResponseEntity.created(new URI("/api/expense" + result.getId())).body(result);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
