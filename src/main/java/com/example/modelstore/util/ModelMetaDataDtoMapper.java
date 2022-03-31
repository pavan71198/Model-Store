package com.example.modelstore.util;

import com.example.modelstore.dto.ModelMetaDataDto;
import com.example.modelstore.entity.ModelMetaData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModelMetaDataDtoMapper {
    public ModelMetaDataDto toModelMetaDataDto(ModelMetaData modelMetaData){
        ModelMetaDataDto modelMetaDataDto = new ModelMetaDataDto();
        modelMetaDataDto.setId(modelMetaData.getId().toString());
        modelMetaDataDto.setFileName(modelMetaData.getFileName());
        modelMetaDataDto.setUserId(modelMetaData.getAppUser().getId().toString());
        modelMetaDataDto.setUploaded(modelMetaData.getUploaded().getTime());
        modelMetaDataDto.setUpdated(modelMetaData.getUpdated().getTime());
        return modelMetaDataDto;
    }

    public List<ModelMetaDataDto> toModelMetaDataDto(List<ModelMetaData> modelMetaDataList){
        List<ModelMetaDataDto> modelMetaDataDtoList = new ArrayList<>();
        for (ModelMetaData modelMetaData: modelMetaDataList){
            modelMetaDataDtoList.add(toModelMetaDataDto(modelMetaData));
        }
        return modelMetaDataDtoList;
    }
}
