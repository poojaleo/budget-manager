package com.java.expensetracker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "category")
public class Category {

    private String categoryName;

    private BigDecimal categoryBudget;

    @DynamoDBHashKey(attributeName = "categoryName")
    public String getCategoryName() {
        return categoryName;
    }
    @DynamoDBAttribute(attributeName = "categoryBudget")
    public BigDecimal getCategoryBudget() {
        return categoryBudget;
    }
}
