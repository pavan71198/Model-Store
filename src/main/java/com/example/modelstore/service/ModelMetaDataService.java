package com.example.modelstore.service;

import com.example.modelstore.dto.ModelMetaDataDto;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ModelMetaDataService {
    void upload(MultipartFile uploadFile, String fileName, Authentication authentication);
    void update(String id, MultipartFile uploadFile, String fileName, Authentication authentication);
    HttpEntity<byte[]> download(String id, Authentication authentication);
    void delete(String id, Authentication authentication);
    ModelMetaDataDto fetchById(String id, Authentication authentication);
    List<ModelMetaDataDto> fetchByUser(Authentication authentication);
}
