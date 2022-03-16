package com.java.expensetracker.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.java.expensetracker.model.Category;
import com.java.expensetracker.model.Expense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DynamoDbRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbRepository.class);

    @Autowired
    private DynamoDBMapper mapper;

    public Category getCategory(String categoryName) {
        return mapper.load(Category.class, categoryName);
    }

    public void createCategory(Category category) {
        mapper.save(category);
    }

    public void createCategory(String categoryName) {
        Category category = new Category(categoryName, null);
        createCategory(category);
    }

    public void updateCategory(Category category) {
        try {
            mapper.save(category, buildDynamoDBSaveExpressionCategory(category));

        } catch (ConditionalCheckFailedException exception) {
            LOGGER.error("Invalid Data" + exception.getMessage());
        }
    }

    private DynamoDBSaveExpression buildDynamoDBSaveExpressionCategory(Category category) {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        //Will throw exception if id not equal
        expectedAttributeValueMap.put("categoryName", new ExpectedAttributeValue(new AttributeValue(category.getCategoryName())))
                .withComparisonOperator(ComparisonOperator.EQ);
        saveExpression.setExpected(expectedAttributeValueMap);
        return saveExpression;
    }

    public void deleteCategory(Category category) {
        mapper.delete(category);
    }

    public void deleteCategoryByName(String categoryName) {
        Category category = mapper.load(Category.class, categoryName);
        deleteCategory(category);
    }

    public Expense getExpense(String expenseId, String expenseDate) {
        return mapper.load(Expense.class, expenseId, expenseDate);
    }

    public void createExpense(Expense expense) {
        mapper.save(expense);
    }

    public void deleteExpense(Expense expense) {
        mapper.delete(expense);
    }

    public void updateExpense(Expense expense) {
        try {
            mapper.save(expense, buildDynamoDBSaveExpressionExpense(expense));

        } catch (ConditionalCheckFailedException exception) {
            LOGGER.error("Invalid Data" + exception.getMessage());
        }
    }

    private DynamoDBSaveExpression buildDynamoDBSaveExpressionExpense(Expense expense) {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        //Will throw exception if id not equal
        expectedAttributeValueMap.put("expenseId", new ExpectedAttributeValue(new AttributeValue(expense.getExpenseId())))
                .withComparisonOperator(ComparisonOperator.EQ);
        saveExpression.setExpected(expectedAttributeValueMap);
        return saveExpression;
    }

}
