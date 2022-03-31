package com.example.modelstore.service;

import com.example.modelstore.dto.AppUserDto;
import com.example.modelstore.entity.AppUser;
import com.example.modelstore.repository.AppUserRepository;
import com.example.modelstore.util.AppUserDtoMapper;
import com.example.modelstore.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserDtoMapper appUserDtoMapper;

    @Autowired
    private UUIDUtil uuidUtil;

    public AppUser fetchById(String id){
        UUID appUserId = uuidUtil.toUUID(id);
        Optional<AppUser> appUserMatch = appUserRepository.findById(appUserId);
        AppUser appUser;
        if (appUserMatch.isPresent()){
            appUser = appUserMatch.get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        return appUser;
    }

    public AppUserDto fetch(Authentication authentication){
        String id = authentication.getPrincipal().toString();
        AppUser appUser = fetchById(id);
        return appUserDtoMapper.toAppUserDto(appUser);
    }
}
