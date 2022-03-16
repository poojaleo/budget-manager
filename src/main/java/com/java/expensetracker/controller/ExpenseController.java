package com.java.expensetracker.controller;

import com.java.expensetracker.model.Expense;
import com.java.expensetracker.repository.ExpenseRepository;
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

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping("/expense")
    public ResponseEntity<Collection<Expense>> getAllExpenses() {
        Iterable<Expense> expenseIterator = expenseRepository.findAll();
        Collection<Expense> expenses = new ArrayList<>();

        for(Expense expense : expenseIterator) {
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
    public ResponseEntity<?> createExpense(@RequestBody Expense expense) {
        Optional<Expense> optionalExpense = this.expenseRepository.findById(expense.getExpenseId());

        if(optionalExpense.isPresent()) {
            return new ResponseEntity<>(String.format(
                    "Id: %s is already in use. Send a valid request", expense.getExpenseId()), HttpStatus.CONFLICT);
        }

        Expense result = expenseRepository.save(expense);
        try {
            return ResponseEntity.created(new URI("/api/expense" + result.getExpenseId())).body(result);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the expense", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
