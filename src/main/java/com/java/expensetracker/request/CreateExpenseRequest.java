package com.java.expensetracker.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor
@Data
public class CreateExpenseRequest {
    @NonNull
    private String expenseDate;
    private String description;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String categoryName;
    private String merchant;
}
