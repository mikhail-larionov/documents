package com.example.documents.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DynamoDbLocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {
    public String convert(LocalDateTime localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public LocalDateTime unconvert(String string) {
        if (string != null)
            return LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        else return null;
    }

}