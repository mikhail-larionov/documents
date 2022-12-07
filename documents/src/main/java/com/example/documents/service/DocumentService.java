package com.example.documents.service;

import com.example.documents.model.entity.Document;
import com.example.documents.repository.DocumentRepository;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final MinioService minioService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DocumentService(DocumentRepository documentRepository, MinioService minioService) {
        this.documentRepository = documentRepository;
        this.minioService = minioService;
    }

    public void saveDocument(Document document) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("Saving" + document);
        Document savedDocument = documentRepository.save(document);
        log.info("Saving document by id");
        minioService.saveDocument(savedDocument.getId());
    }
}
