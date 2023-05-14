package com.example.documents.service;

import com.example.documents.model.enums.TemplateType;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.template-bucket}")
    private String templateBucket;
    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void saveDocument(String userId, InputStream inputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        }
        System.out.println("userId - " + userId);
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(userId + "/document.docx")
                        .stream(inputStream, -1, 10485760)
                        .build()
        );
    }

    public GetObjectResponse getDocumentById(String userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(userId + "/document.docx")
                        .build());
    }

    public GetObjectResponse getDocumentTemplateByTemplateType(TemplateType templateType) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String filePath = templateType + "/document.docx";
        if (!checkFileExisting(filePath, templateBucket)) {
            throw new IllegalArgumentException("Template " + templateType + "doesn't exist");
        }
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(templateBucket)
                        .object(filePath)
                        .build());
    }

    public GetObjectResponse getImageById(String userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String pathToCheck = userId + "/image.jpg";
        String filePath = checkFileExisting(pathToCheck, bucket) ? pathToCheck : "default/default.jpg";
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(bucket)
                        .object(filePath)
                        .build());
    }

    public Boolean checkFileExisting(String path, String bucket) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket)
                    .object(path).build());
            return true;
        } catch (ErrorResponseException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
