package com.example.producer.model.request;

import com.example.producer.model.enums.TemplateType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentCreateRequest {
    private TemplateType documentTemplateType;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "documentTemplateType")
    @JsonSubTypes(@JsonSubTypes.Type(value = IdDocumentMetadata.class, name = "ID"))
    private DocumentMetadata documentMetadata;
}
