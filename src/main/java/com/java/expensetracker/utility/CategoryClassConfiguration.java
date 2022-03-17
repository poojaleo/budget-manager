package com.java.expensetracker.utility;

import com.java.expensetracker.controller.CategoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryClassConfiguration {
    @Autowired
    private CategoryController categoryController;


}
