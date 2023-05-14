package com.example.documents.controller;

import com.example.documents.service.DocumentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final DocumentService documentService;

    public TestController(DocumentService documentService) {
        this.documentService = documentService;
    }


}

