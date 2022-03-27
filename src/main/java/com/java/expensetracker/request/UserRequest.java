package com.java.expensetracker.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class UserRequest {
    private String fullName;
    private String emailAddress;
    private String password;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyBudget;
}
