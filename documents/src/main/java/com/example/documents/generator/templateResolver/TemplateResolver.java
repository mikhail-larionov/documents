package com.example.documents.generator.templateResolver;

import com.example.documents.model.dto.requests.DocumentCreateRequest;
import com.example.documents.model.enums.TemplateType;
import io.minio.errors.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface TemplateResolver {
    TemplateType getTemplateType();

    InputStream generateDocument(DocumentCreateRequest documentCreateRequest) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, InvalidFormatException;
}
