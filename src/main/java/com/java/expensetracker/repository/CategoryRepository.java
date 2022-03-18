package com.java.expensetracker.repository;

import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.CategoryId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@EnableScan
@Repository
public interface CategoryRepository extends CrudRepository<Category, CategoryId> {
    Iterable<Category> findByUsername(String username);
}
