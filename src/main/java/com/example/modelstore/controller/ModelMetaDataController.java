package com.example.modelstore.controller;

import com.example.modelstore.dto.ModelMetaDataDto;
import com.example.modelstore.service.ModelMetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/model")
public class ModelMetaDataController {
    @Autowired
    ModelMetaDataService modelMetaDataService;

    @PostMapping("/upload")
    public void upload(@RequestParam("modelFile") MultipartFile uploadFile, @RequestParam("name") String fileName, Authentication authentication) {
        modelMetaDataService.upload(uploadFile, fileName, authentication);
    }

    @PostMapping("/update/{id}")
    public void update(@PathVariable String id, @RequestParam("modelFile") MultipartFile uploadFile, @RequestParam("name") String fileName, Authentication authentication) {
        modelMetaDataService.update(id, uploadFile, fileName, authentication);
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public HttpEntity<byte[]> download(@PathVariable String id, Authentication authentication) {
        return modelMetaDataService.download(id, authentication);
    }

    @GetMapping("/info/{id}")
    public ModelMetaDataDto fetchById(@PathVariable String id, Authentication authentication){
        return modelMetaDataService.fetchById(id, authentication);
    }

    @GetMapping("/info/list")
    public List<ModelMetaDataDto> fetchByUser(Authentication authentication){
        return modelMetaDataService.fetchByUser(authentication);
    }
}
