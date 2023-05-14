package com.example.documents.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.example.documents.model.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTemplate {
    @Id
    @DynamoDBHashKey
    private TemplateType templateType;
    private String name;
}
