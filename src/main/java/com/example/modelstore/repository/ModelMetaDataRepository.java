package com.example.modelstore.repository;

import com.example.modelstore.entity.AppUser;
import com.example.modelstore.entity.ModelMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelMetaDataRepository extends JpaRepository<ModelMetaData, UUID> {
    List<ModelMetaData> findModelMetaDataByAppUserOrderByUpdatedDesc(AppUser appUser);
}
