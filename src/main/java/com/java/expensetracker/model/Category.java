package com.java.expensetracker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@AllArgsConstructor
@Data
@DynamoDBTable(tableName = "category")
public class Category {

    private String categoryName;

    private BigDecimal categoryBudget;

    @DynamoDBHashKey
    public String getCategoryName() {
        return categoryName;
    }
    @DynamoDBAttribute
    public BigDecimal getCategoryBudget() {
        return categoryBudget;
    }
}
