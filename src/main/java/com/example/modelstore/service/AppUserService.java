package com.example.modelstore.service;

import com.example.modelstore.dto.AppUserDto;
import com.example.modelstore.entity.AppUser;
import org.springframework.security.core.Authentication;

public interface AppUserService {
    AppUser fetchById(String id);
    AppUserDto fetch(Authentication authentication);
}
