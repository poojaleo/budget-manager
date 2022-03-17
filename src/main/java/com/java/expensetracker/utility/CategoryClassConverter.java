package com.java.expensetracker.utility;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.java.expensetracker.model.Category;

public class CategoryClassConverter implements DynamoDBTypeConverter<String, Category> {

    @Override
    public String convert(Category object) {
        return object.getCategoryName();
    }

    @Override
    public Category unconvert(String object) {
        return new Category(object, null);

        /*Optional<Category> optionalCategory = categoryController.getCategoryConversionMethod(object);

        if(!optionalCategory.isPresent()) {
            return null;
        }
        return optionalCategory.get();*/
    }
}
