package com.java.expensetracker.request;

import com.java.expensetracker.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateExpenseRequest {
    private String expenseDate;
    private String description;
    private BigDecimal amount;
    private Category category;
}
