package com.java.expensetracker.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class CreateUserRequest extends UserRequest {
    @NonNull
    private String username;
}
