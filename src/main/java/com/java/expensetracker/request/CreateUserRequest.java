package com.java.expensetracker.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class CreateUserRequest {
    @NonNull
    private String username;
    @NonNull
    private String fullName;
    @NonNull
    private String emailAddress;
    @NonNull
    private String password;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyBudget;

}
