package com.example.documents.kafka;

import com.example.documents.model.dto.requests.DocumentCreateRequest;
import com.example.documents.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.minio.errors.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class Listener {

    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

    public Listener(DocumentService documentService, ObjectMapper objectMapper) {
        this.documentService = documentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "user", groupId = "document", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, InvalidFormatException {
        System.out.println("Received Message in group foo: " + message);
        DocumentCreateRequest document = objectMapper.readValue(message, DocumentCreateRequest.class);
        documentService.saveDocument(document);
    }
}
