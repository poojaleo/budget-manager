package com.java.expensetracker.repository;

import com.java.expensetracker.model.Expense;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
@EnableDynamoDBRepositories
public interface ExpenseRepository extends CrudRepository<Expense, String> {

}
