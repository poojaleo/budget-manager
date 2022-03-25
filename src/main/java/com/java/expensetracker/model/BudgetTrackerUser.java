package com.java.expensetracker.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Setter
@DynamoDBTable(tableName = "users")
public class BudgetTrackerUser {
    @NonNull
    @NotEmpty
    @Id
    private String username;
    @NonNull
    @NotEmpty
    private String fullName;
    @NonNull
    @NotEmpty
    private String emailAddress;
    @NonNull
    @NotEmpty
    private String password;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyBudget;
    private Timestamp createdAt;


    @DynamoDBHashKey(attributeName = "username")
    public String getUsername() {
        return username;
    }

    @DynamoDBAttribute(attributeName = "fullName")
    public String getFullName() {
        return fullName;
    }

    @DynamoDBAttribute
    public String getEmailAddress() {
        return emailAddress;
    }

    @DynamoDBAttribute
    public String getPassword() {
        return password;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    @DynamoDBAttribute
    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.N)
    @DynamoDBAttribute
    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }

    @DynamoDBTypeConvertedTimestamp
    @DynamoDBAttribute
    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
