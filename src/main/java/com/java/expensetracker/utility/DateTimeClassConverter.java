package com.java.expensetracker.utility;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.sql.Timestamp;

public class DateTimeClassConverter implements DynamoDBTypeConverter<String, Timestamp> {
    @Override
    public String convert(Timestamp object) {
        return object.toString();
    }

    @Override
    public Timestamp unconvert(String object) {
        return Timestamp.valueOf(object);
    }
}
