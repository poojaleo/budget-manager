package com.java.expensetracker.controller;

import com.java.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String viewHomePage() {
        return "index";
    }
}
