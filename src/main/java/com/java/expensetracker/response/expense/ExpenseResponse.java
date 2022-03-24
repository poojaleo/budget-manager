package com.java.expensetracker.response.expense;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExpenseResponse {
    private String username;
    private String expenseId;
    private String expenseDate;
    private String description;
    private BigDecimal amount;
    private String categoryName;
    private String merchant;
}
