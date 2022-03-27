package com.java.expensetracker.response.budgettrackeruser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserResponse {
    private String fullName;
    private String emailAddress;
    private String createdAt;
}
