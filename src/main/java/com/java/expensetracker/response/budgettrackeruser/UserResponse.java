package com.java.expensetracker.response.budgettrackeruser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;
    private String fullName;
    private String emailAddress;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyBudget;
    private String createdAt;
}
