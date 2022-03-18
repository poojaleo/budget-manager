package com.java.expensetracker.response.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryResponse {
    private String username;
    private String categoryName;
    private BigDecimal categoryBudget;
}
