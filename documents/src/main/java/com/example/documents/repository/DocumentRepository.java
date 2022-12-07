package com.example.documents.repository;

import com.example.documents.model.entity.Document;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface DocumentRepository extends CrudRepository<Document, String> {
}
