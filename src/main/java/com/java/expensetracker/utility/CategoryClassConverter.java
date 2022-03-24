package com.java.expensetracker.utility;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.CategoryId;

public class CategoryClassConverter implements DynamoDBTypeConverter<String, Category> {

    @Override
    public String convert(Category object) {
        return object.getCategoryName();
    }

    @Override
    public Category unconvert(String object) {
        return new Category(new CategoryId(null, object), null);
    }
}
