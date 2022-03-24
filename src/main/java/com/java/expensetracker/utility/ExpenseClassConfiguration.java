package com.java.expensetracker.utility;

import com.java.expensetracker.controller.ExpenseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseClassConfiguration {
    @Autowired
    private ExpenseController expenseController;
}
