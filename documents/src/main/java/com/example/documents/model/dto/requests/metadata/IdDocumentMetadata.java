package com.example.documents.model.dto.requests.metadata;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.example.documents.converter.DynamoDbLocalDateConverter;
import com.example.documents.model.enums.Gender;
import com.example.documents.model.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@DynamoDBDocument
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IdDocumentMetadata extends DocumentMetadata {
    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    @DynamoDBTypeConvertedEnum
    private Gender gender;
    private String passportNumber;
    private String city;
    private String livingAddress;
    @DynamoDBTypeConverted(converter = DynamoDbLocalDateConverter.class)
    private LocalDate passportLastUpdateDate;
    @DynamoDBTypeConvertedEnum
    private TemplateType templateType;
}
