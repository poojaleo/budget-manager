package com.java.expensetracker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;


@NoArgsConstructor
@Data
@DynamoDBTable(tableName = "category")
public class Category {

    @Id
    @DynamoDBIgnore
    private CategoryId categoryId;
    private BigDecimal categoryBudget;

    public Category(CategoryId categoryId) {
        this.categoryId = categoryId;
        this.categoryBudget = null;
    }

    public Category(CategoryId categoryId, BigDecimal categoryBudget) {
        this.categoryId = categoryId;
        this.categoryBudget = categoryBudget;
    }


    @DynamoDBHashKey(attributeName = "username")
    public String getUsername() {
        return categoryId != null ? categoryId.getUsername() : null;
    }

    @DynamoDBRangeKey(attributeName = "categoryName")
    public String getCategoryName() {
        return categoryId != null ? categoryId.getCategoryName() : null;
    }

    @DynamoDBAttribute(attributeName = "categoryBudget")
    public BigDecimal getCategoryBudget() {
        return categoryBudget;
    }

    public void setUsername(String username) {
        if(categoryId == null) {
            categoryId = new CategoryId();
        }
        categoryId.setUsername(username);
    }

    public void setCategoryName(String categoryName) {
        if(categoryId == null) {
            categoryId = new CategoryId();
        }
        categoryId.setCategoryName(categoryName);
    }
}
