package com.java.expensetracker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@DynamoDBTable(tableName = "expenses")
public class Expense {

    @Id
    @DynamoDBIgnore
    private ExpenseKey expenseKey;
    private String expenseDate;
    private String description;
    private BigDecimal amount;
    private String categoryName;
    private String merchant;


    @DynamoDBHashKey(attributeName = "username")
    public String getUsername() {
        return expenseKey != null ? expenseKey.getUsername() : null;
    }

    @DynamoDBRangeKey(attributeName = "expenseId")
    public String getExpenseId() {
        return expenseKey != null ? expenseKey.getExpenseId() : null;
    }


    @DynamoDBAttribute
    public String getExpenseDate() {
        return expenseDate;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }


    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    @DynamoDBAttribute(attributeName = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @DynamoDBAttribute(attributeName = "categoryName")
    public String getCategoryName() {
        return categoryName;
    }

    public void setUsername(String username) {
        if(expenseKey == null) {
            expenseKey = new ExpenseKey();
        }
        expenseKey.setUsername(username);
    }

    public void setExpenseId(String expenseId) {
        if(expenseKey == null) {
            expenseKey = new ExpenseKey();
        }
        expenseKey.setExpenseId(expenseId);
    }

}
