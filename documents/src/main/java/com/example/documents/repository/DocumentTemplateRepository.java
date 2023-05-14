package com.example.documents.repository;

import com.example.documents.model.entity.DocumentTemplate;
import com.example.documents.model.enums.TemplateType;
import org.springframework.data.repository.CrudRepository;

public interface DocumentTemplateRepository extends CrudRepository<DocumentTemplate, TemplateType> {

}
