package com.java.expensetracker.repository;

import com.java.expensetracker.model.BudgetTrackerUsers;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface UserRepository extends CrudRepository<BudgetTrackerUsers, String> {
}
