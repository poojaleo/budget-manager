package com.java.expensetracker.utility;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.java.expensetracker.model.Category;
import com.java.expensetracker.repository.DynamoDbRepository;

public class CategoryClassConverter implements DynamoDBTypeConverter<String, Category> {

    private DynamoDbRepository repository;

    @Override
    public String convert(Category object) {
        return object.getCategoryName();
    }

    @Override
    public Category unconvert(String object) {
        return repository.getCategory(object);
    }
}
