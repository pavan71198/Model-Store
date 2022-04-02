package com.example.modelstore.service.s3;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;
import java.util.Map;

public interface AmazonS3Service {
    PutObjectResult upload (String path, String name, Map<String, String> optionalMetaData, InputStream inputStream);
    S3Object download(String path, String name);
    void delete(String path, String name);
}
