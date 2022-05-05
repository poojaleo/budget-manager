package com.java.expensetracker.controller;

import com.java.expensetracker.exception.InvalidUserException;
import com.java.expensetracker.model.BudgetTrackerUser;
import com.java.expensetracker.repository.UserRepository;
import com.java.expensetracker.request.CreateUserRequest;
import com.java.expensetracker.request.UpdateUserRequest;
import com.java.expensetracker.request.UserRequest;
import com.java.expensetracker.response.budgettrackeruser.AllUserResponse;
import com.java.expensetracker.response.budgettrackeruser.UserResponse;
import com.java.expensetracker.utility.ExpenseTrackerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        Iterable<BudgetTrackerUser> budgetTrackerUserIterable = userRepository.findAll();

        if(((Collection<BudgetTrackerUser>) budgetTrackerUserIterable).size() == 0) {
            return new ResponseEntity<>("No Users in the database", HttpStatus.OK);
        }

        Collection<AllUserResponse> budgetTrackerUsers = new ArrayList<>();

        for(BudgetTrackerUser budgetTrackerUser : budgetTrackerUserIterable) {
            budgetTrackerUsers.add(new AllUserResponse(budgetTrackerUser.getFullName(),
                    budgetTrackerUser.getEmailAddress(), budgetTrackerUser.getCreatedAt()));
        }

        return new ResponseEntity<>(budgetTrackerUsers, HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        Optional<BudgetTrackerUser> optionalBudgetTrackerUser = userRepository.findById(username);

        if(!optionalBudgetTrackerUser.isPresent()) {
            return new ResponseEntity<>(String.format("Username: %s does not exist", username), HttpStatus.NOT_FOUND);
        }

        BudgetTrackerUser budgetTrackerUser = optionalBudgetTrackerUser.get();

        UserResponse userResponse = new UserResponse(budgetTrackerUser.getUsername(), budgetTrackerUser.getFullName(),
                budgetTrackerUser.getEmailAddress(), budgetTrackerUser.getMonthlyIncome(),
                budgetTrackerUser.getMonthlyBudget(), budgetTrackerUser.getCreatedAt());

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        logger.info(createUserRequest.toString());
        try {
            Optional<BudgetTrackerUser> budgetTrackerUserOptional = userRepository.findById(createUserRequest.getUsername());
            logger.info(budgetTrackerUserOptional.toString());
            if(budgetTrackerUserOptional.isPresent()) {
                return new ResponseEntity<>(String.format("Username: %s already exists", createUserRequest.getUsername()),
                        HttpStatus.BAD_REQUEST);
            }

            Optional<BudgetTrackerUser> budgetTrackerUserEmailCheck = userRepository.findByEmailAddress(createUserRequest.getEmailAddress());

            if(budgetTrackerUserEmailCheck.isPresent()) {
                return new ResponseEntity<>(String.format("Email Address: %s already exists",
                        createUserRequest.getEmailAddress()), HttpStatus.BAD_REQUEST);
            }
            BudgetTrackerUser budgetTrackerUser = new BudgetTrackerUser();

            try {
                budgetTrackerUser = createOrUpdateUser(budgetTrackerUser, createUserRequest, createUserRequest.getUsername());
            } catch (InvalidUserException invalidUserException) {
                return new ResponseEntity<>(invalidUserException.getMessage(), HttpStatus.BAD_REQUEST);
            }

            budgetTrackerUser.setCreatedAt(new Timestamp(System.currentTimeMillis()).toString());

            BudgetTrackerUser result = userRepository.save(budgetTrackerUser);

            UserResponse userResponse = new UserResponse(result.getUsername(), result.getFullName(), result.getEmailAddress(),
                    result.getMonthlyIncome(), result.getMonthlyBudget(), result.getCreatedAt());

            String url = "/api" + createUserRequest.getUsername();
            url = encodeStringToUrl(url);

            try {
                return ResponseEntity.created(new URI(url)).body(userResponse);
            } catch (URISyntaxException uriSyntaxException) {
                return new ResponseEntity<>("Was unable to create the user", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,
                                        @RequestBody UpdateUserRequest updateUserRequest) {

        Optional<BudgetTrackerUser> budgetTrackerUserOptional = userRepository.findById(username);

        if(!budgetTrackerUserOptional.isPresent()) {
            return new ResponseEntity<>(String.format("Username: %s does not exists", username),
                    HttpStatus.BAD_REQUEST);
        }

        BudgetTrackerUser budgetTrackerUser = budgetTrackerUserOptional.get();

        try {
            budgetTrackerUser = createOrUpdateUser(budgetTrackerUser, updateUserRequest, username);
        } catch (InvalidUserException invalidUserException) {
            return new ResponseEntity<>(invalidUserException.getMessage(), HttpStatus.BAD_REQUEST);
        }

        BudgetTrackerUser result = userRepository.save(budgetTrackerUser);

        UserResponse userResponse = new UserResponse(result.getUsername(), result.getFullName(), result.getEmailAddress(),
                result.getMonthlyIncome(), result.getMonthlyBudget(), result.getCreatedAt());

        String url = "/api" + username;
        url = encodeStringToUrl(url);

        try {
            return ResponseEntity.created(new URI(url)).body(userResponse);
        } catch (URISyntaxException uriSyntaxException) {
            return new ResponseEntity<>("Was unable to create the user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String encodeStringToUrl(String name) {
        return name.replace(" ", "+");
    }

    public BudgetTrackerUser createOrUpdateUser(BudgetTrackerUser budgetTrackerUser, UserRequest request,
                                                String username) throws InvalidUserException {
        if(request instanceof CreateUserRequest) {
            boolean isValidUsername = ExpenseTrackerUtility.isValidString(username);
            if(isValidUsername) {
                budgetTrackerUser.setUsername(username);
            } else {
                throw new InvalidUserException(String.format("Invalid username: %s pattern", username));
            }
        }

        if(request.getFullName() != null) {
            boolean isValidFullName = ExpenseTrackerUtility.isValidString(request.getFullName());
            if(isValidFullName) {
                budgetTrackerUser.setFullName(request.getFullName());
            } else {
                throw new InvalidUserException(String.format("Invalid name: %s pattern", request.getFullName()));
            }
        }

        if(request.getEmailAddress() != null) {
            boolean isValidEmailAddress = ExpenseTrackerUtility.isValidEmailAddress(request.getEmailAddress());
            if(isValidEmailAddress) {
                budgetTrackerUser.setEmailAddress(request.getEmailAddress());
            } else {
                throw new InvalidUserException(String.format("Invalid Email Address: %s", request.getEmailAddress()));
            }
        }

        if(request.getPassword() != null) {
            boolean isValidPassword = ExpenseTrackerUtility.isValidPassword(request.getPassword());
            if(isValidPassword) {
                budgetTrackerUser.setPassword(request.getPassword());
            } else {
                throw new InvalidUserException("Invalid Password. Password should contain minimum eight characters, " +
                        "at least one uppercase letter, one lowercase letter, one number and one special character");
            }
        }

        if(request.getMonthlyIncome() != null) {
            budgetTrackerUser.setMonthlyIncome(request.getMonthlyIncome());
        }
        if(request.getMonthlyBudget() != null) {
            budgetTrackerUser.setMonthlyBudget(request.getMonthlyBudget());
        }

        return budgetTrackerUser;
    }


}
