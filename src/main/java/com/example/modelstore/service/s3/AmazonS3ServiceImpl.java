package com.example.modelstore.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service{

    @Autowired
    private AmazonS3 amazonS3;

    public PutObjectResult upload(String path, String name, Map<String, String> metaData, InputStream inputStream){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        for (Map.Entry<String,String> mapElement : metaData.entrySet()) {
            objectMetadata.addUserMetadata(mapElement.getKey(), mapElement.getValue());
        }
        return amazonS3.putObject(path, name, inputStream, objectMetadata);
    }

    public S3Object download(String path, String name) {
        return amazonS3.getObject(path, name);
    }
}
