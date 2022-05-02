package com.java.expensetracker.request;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRequest {
    private String emailAddress;
    private String password;
    private String fullName;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyBudget;

    public UserRequest(String password, String emailAddress) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.fullName = null;
        this.monthlyIncome = null;
        this.monthlyBudget = null;
    }

    public UserRequest(String emailAddress, String password, String fullName, BigDecimal monthlyIncome, BigDecimal monthlyBudget) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.fullName = fullName;
        this.monthlyIncome = monthlyIncome;
        this.monthlyBudget = monthlyBudget;
    }

    public UserRequest() {
    }
}
