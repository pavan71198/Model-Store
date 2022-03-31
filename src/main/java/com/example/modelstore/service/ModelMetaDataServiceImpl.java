package com.example.modelstore.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.example.modelstore.dto.ModelMetaDataDto;
import com.example.modelstore.entity.AppUser;
import com.example.modelstore.entity.ModelMetaData;
import com.example.modelstore.repository.ModelMetaDataRepository;
import com.example.modelstore.service.s3.AmazonS3Service;
import com.example.modelstore.util.ModelMetaDataDtoMapper;
import com.example.modelstore.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@Service
public class ModelMetaDataServiceImpl implements ModelMetaDataService {

    @Autowired
    private ModelMetaDataRepository modelMetaDataRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ModelMetaDataDtoMapper modelMetaDataDtoMapper;

    @Autowired
    private UUIDUtil uuidUtil;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public void upload(MultipartFile uploadFile, String fileName, Authentication authentication) {
        Date date = new Date();
        AppUser appUser = appUserService.fetchById(authentication.getPrincipal().toString());

        if (uploadFile.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Upload file empty");
        }
        if (fileName == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File name not specified");
        }
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", uploadFile.getContentType());
        metadata.put("Content-Length", String.valueOf(uploadFile.getSize()));

        ModelMetaData modelMetaData = new ModelMetaData();
        modelMetaDataRepository.save(modelMetaData);
        String path = bucketName+"/"+appUser.getId().toString();
        String id = modelMetaData.getId().toString();
        try {
            PutObjectResult putObjectResult = amazonS3Service.upload(path, id, metadata, uploadFile.getInputStream());
            modelMetaData.setFileName(fileName);
            modelMetaData.setAppUser(appUser);
            modelMetaData.setUploaded(date);
            modelMetaData.setUpdated(date);
            modelMetaDataRepository.save(modelMetaData);
        }
        catch (IOException exception) {
            modelMetaDataRepository.delete(modelMetaData);
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Couldn't upload file");
        }
        throw new ResponseStatusException(HttpStatus.OK);
    }

    public void update(String id, MultipartFile uploadFile, String fileName, Authentication authentication) {
        Date date = new Date();
        AppUser appUser = appUserService.fetchById(authentication.getPrincipal().toString());

        if (uploadFile.isEmpty() && fileName == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes specified");
        }

        ModelMetaData modelMetaData = fetchById(id);

        if (!modelMetaData.getAppUser().equals(appUser)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "File doesn't belong to this user");
        }

        if (fileName != null){
            modelMetaData.setFileName(fileName);
        }

        if (!uploadFile.isEmpty()){
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", uploadFile.getContentType());
            metadata.put("Content-Length", String.valueOf(uploadFile.getSize()));
            String path = bucketName+"/"+appUser.getId().toString();
            try {
                amazonS3Service.upload(path, id, metadata, uploadFile.getInputStream());
            }
            catch (IOException exception) {
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Couldn't update file");
            }
        }

        modelMetaData.setUpdated(date);
        modelMetaDataRepository.save(modelMetaData);

        throw new ResponseStatusException(HttpStatus.OK);
    }

    public HttpEntity<byte[]> download(String id, Authentication authentication){
        AppUser appUser = appUserService.fetchById(authentication.getPrincipal().toString());

        ModelMetaData modelMetaData = fetchById(id);

        if (!modelMetaData.getAppUser().equals(appUser)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "File doesn't belong to this user");
        }

        String path = bucketName+"/"+appUser.getId().toString();
        try {
            S3Object s3Object = amazonS3Service.download(path, id);
            String contentType = s3Object.getObjectMetadata().getContentType();
            byte[] bytes = s3Object.getObjectContent().readAllBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(contentType));
            headers.setContentLength(bytes.length);
            return new HttpEntity<>(bytes, headers);
        }
        catch (IOException exception){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Couldn't download file");
        }
    }

    public ModelMetaDataDto fetchById(String id, Authentication authentication){
        AppUser appUser = appUserService.fetchById(authentication.getPrincipal().toString());
        ModelMetaData modelMetaData = fetchById(id);
        if (!modelMetaData.getAppUser().equals(appUser)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "File doesn't belong to this user");
        }
        return modelMetaDataDtoMapper.toModelMetaDataDto(modelMetaData);
    }

    public List<ModelMetaDataDto> fetchByUser(Authentication authentication){
        AppUser appUser = appUserService.fetchById(authentication.getPrincipal().toString());

        List<ModelMetaData> modelMetaDataList = modelMetaDataRepository.findModelMetaDataByAppUserOrderByUpdatedDesc(appUser);

        return modelMetaDataDtoMapper.toModelMetaDataDto(modelMetaDataList);
    }

    private ModelMetaData fetchById(String id){
        Optional<ModelMetaData> modelMetaDataMatch = modelMetaDataRepository.findById(uuidUtil.toUUID(id));
        if (modelMetaDataMatch.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested File Not Found");
        }
        return modelMetaDataMatch.get();
    }
}
