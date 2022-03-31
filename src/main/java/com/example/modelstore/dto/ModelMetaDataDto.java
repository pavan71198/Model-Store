package com.example.modelstore.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ModelMetaDataDto {
    String id;
    String fileName;
    String userId;
    Long uploaded;
    Long updated;
}
