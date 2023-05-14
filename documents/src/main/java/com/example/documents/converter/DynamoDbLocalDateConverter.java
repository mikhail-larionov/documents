package com.example.documents.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DynamoDbLocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {
    public String convert(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public LocalDate unconvert(String string) {
        if (string != null)
            return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE);
        else return null;
    }

}
