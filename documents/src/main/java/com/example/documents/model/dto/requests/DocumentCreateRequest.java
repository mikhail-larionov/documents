package com.example.documents.model.dto.requests;

import com.example.documents.model.dto.requests.metadata.DocumentMetadata;
import com.example.documents.model.dto.requests.metadata.IdDocumentMetadata;
import com.example.documents.model.enums.TemplateType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
public class DocumentCreateRequest {
    private TemplateType documentTemplateType;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "documentTemplateType")
    @JsonSubTypes(@JsonSubTypes.Type(value = IdDocumentMetadata.class, name = "ID"))
    private DocumentMetadata documentMetadata;
}
