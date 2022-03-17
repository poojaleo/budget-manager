package com.java.expensetracker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.java.expensetracker.request.CreateExpenseRequest;
import com.java.expensetracker.utility.CategoryClassConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "expense")
public class Expense {


    private String expenseId;
    private String expenseDate;
    private String description;
    private BigDecimal amount;
    private Category category;

    public Expense(String expenseId, CreateExpenseRequest expenseRequest) {
        this.expenseId = expenseId;
        this.expenseDate = expenseRequest.getExpenseDate();
        this.description = expenseRequest.getDescription();
        this.amount = expenseRequest.getAmount();
        this.category = expenseRequest.getCategory();
    }

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getExpenseId() {
        return expenseId;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute
    public BigDecimal getAmount() {
        return amount;
    }

    @DynamoDBTypeConverted(converter = CategoryClassConverter.class)
    @DynamoDBAttribute
    public Category getCategory() {
        return category;
    }

}
