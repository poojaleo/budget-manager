package com.java.expensetracker.repository;

import com.java.expensetracker.model.Expense;
import com.java.expensetracker.model.ExpenseKey;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@EnableScan
@Repository
public interface ExpenseRepository extends CrudRepository<Expense, ExpenseKey> {
    Iterable<Expense> findByUsername(String username);
}
