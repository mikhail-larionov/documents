package com.example.documents.service;

import com.example.documents.generator.templateResolver.TemplateResolver;
import com.example.documents.generator.templateResolver.WordDocGenerator;
import com.example.documents.model.dto.requests.DocumentCreateRequest;
import com.example.documents.model.enums.TemplateType;
import com.example.documents.repository.DocumentRepository;
import io.micrometer.core.annotation.Timed;
import io.minio.GetObjectResponse;
import io.minio.errors.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final WordDocGenerator wordDocGenerator;

    private final Map<TemplateType, TemplateResolver> templateResolverMap;
    private final MinioService minioService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DocumentService(DocumentRepository documentRepository, WordDocGenerator wordDocGenerator, Map<TemplateType, TemplateResolver> templateResolverMap, MinioService minioService) {
        this.documentRepository = documentRepository;
        this.wordDocGenerator = wordDocGenerator;
        this.templateResolverMap = templateResolverMap;
        this.minioService = minioService;
    }

    @Timed(value = "document.time", description = "Time taken to save document")
    public void saveDocument(DocumentCreateRequest documentCreateRequest) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, InvalidFormatException, io.minio.errors.ServerException {
        log.info("Saving" + documentCreateRequest);
//        Document savedDocument = documentRepository.save(document);
        log.info("Saving document by id");
        TemplateResolver templateResolver = templateResolverMap.get(documentCreateRequest.getDocumentTemplateType());
        if (templateResolver == null) {
            throw new IllegalArgumentException();
        }
        System.out.println("userId is --- " + documentCreateRequest.getDocumentMetadata().getUserId());
        InputStream inputStream = templateResolver.generateDocument(documentCreateRequest);
        minioService.saveDocument(documentCreateRequest.getDocumentMetadata().getUserId(), inputStream);
    }

    public GetObjectResponse getDocumentById(String userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, io.minio.errors.ServerException {
        GetObjectResponse documentById = minioService.getDocumentById(userId);
        File targetFile = new File("./document.docx");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.close();
        outStream.write(documentById.readAllBytes());
        return documentById;
    }
}
