package com.java.expensetracker.repository;

import com.java.expensetracker.model.BudgetTrackerUser;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableScan
@Repository
public interface UserRepository extends CrudRepository<BudgetTrackerUser, String> {
    Optional<BudgetTrackerUser> findByEmailAddress(String emailAddress);
    BudgetTrackerUser findByUsername(String username);
}
