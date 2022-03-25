package com.java.expensetracker.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class UpdateExpenseRequest {

    private String expenseDate;
    private String description;
    private BigDecimal amount;
    private String categoryName;
    private String merchant;
}
