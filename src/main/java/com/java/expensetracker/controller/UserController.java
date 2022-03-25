package com.java.expensetracker.controller;

import com.java.expensetracker.model.BudgetTrackerUser;
import com.java.expensetracker.repository.UserRepository;
import com.java.expensetracker.request.CreateUserRequest;
import com.java.expensetracker.response.budgettrackeruser.UserResponse;
import com.java.expensetracker.utility.ExpenseTrackerUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getUser(@PathVariable String username, @RequestParam(required = true) String password) {
        Optional<BudgetTrackerUser> optionalBudgetTrackerUser = userRepository.findById(username);

        if(!optionalBudgetTrackerUser.isPresent()) {
            return new ResponseEntity<>(String.format("Username: %s does not exist", username), HttpStatus.NOT_FOUND);
        }

        BudgetTrackerUser budgetTrackerUser = optionalBudgetTrackerUser.get();

        if(!budgetTrackerUser.getPassword().equals(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }

        UserResponse userResponse = new UserResponse(budgetTrackerUser.getUsername(), budgetTrackerUser.getFullName(),
                budgetTrackerUser.getEmailAddress(), budgetTrackerUser.getMonthlyIncome(),
                budgetTrackerUser.getMonthlyBudget(), budgetTrackerUser.getCreatedAt());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        Optional<BudgetTrackerUser> budgetTrackerUserOptional = userRepository.findById(createUserRequest.getUsername());

        if(budgetTrackerUserOptional.isPresent()) {
            return new ResponseEntity<>(String.format("Username: %s already exists", createUserRequest.getUsername()),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<BudgetTrackerUser> budgetTrackerUserEmailCheck = userRepository.findByEmailAddress(createUserRequest.getEmailAddress());

        if(budgetTrackerUserOptional.isPresent()) {
            return new ResponseEntity<>(String.format("Email Address: %s already exists",
                    createUserRequest.getEmailAddress()), HttpStatus.BAD_REQUEST);
        }
        BudgetTrackerUser budgetTrackerUser = new BudgetTrackerUser();
        boolean isValidUsername = ExpenseTrackerUtility.isValidString(createUserRequest.getUsername());
        if(isValidUsername) {
            budgetTrackerUser.setUsername(createUserRequest.getUsername());
        } else {
            return new ResponseEntity<>(String.format("Invalid username: %s pattern",
                    createUserRequest.getUsername()), HttpStatus.BAD_REQUEST);
        }
        boolean isValidFullName = ExpenseTrackerUtility.isValidString(createUserRequest.getFullName());
        if(isValidFullName) {
            budgetTrackerUser.setFullName(createUserRequest.getFullName());
        } else {
            return new ResponseEntity<>(String.format("Invalid name: %s pattern",
                    createUserRequest.getFullName()), HttpStatus.BAD_REQUEST);
        }
        boolean isValidEmailAddress = ExpenseTrackerUtility.isValidEmailAddress(createUserRequest.getEmailAddress());
        if(isValidEmailAddress) {
            budgetTrackerUser.setEmailAddress(createUserRequest.getEmailAddress());
        } else {
            return new ResponseEntity<>(String.format("Invalid Email Address: %s",
                    createUserRequest.getEmailAddress()), HttpStatus.BAD_REQUEST);
        }
        boolean isValidPassword = ExpenseTrackerUtility.isValidPassword(createUserRequest.getPassword());
        if(isValidPassword) {
            budgetTrackerUser.setPassword(createUserRequest.getPassword());
        } else {
            return new ResponseEntity<>("Invalid Password. Password should contain minimum eight characters, " +
                    "at least one uppercase letter, one lowercase letter, one number and one special character",
                    HttpStatus.BAD_REQUEST);
        }


        /*
        private BigDecimal monthlyIncome;
        private BigDecimal monthlyBudget;
        private Timestamp createdAt;*/

        if(!createUserRequest.getMonthlyIncome().equals(null)) {
            budgetTrackerUser.setMonthlyIncome(createUserRequest.getMonthlyIncome());
        }
        if(!createUserRequest.getMonthlyBudget().equals(null)) {
            budgetTrackerUser.setMonthlyBudget(createUserRequest.getMonthlyBudget());
        }

        budgetTrackerUser.setCreatedAt(new Timestamp(System.currentTimeMillis()));

    }


}
