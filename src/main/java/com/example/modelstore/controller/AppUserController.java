package com.example.modelstore.controller;

import com.example.modelstore.dto.AppUserDto;
import com.example.modelstore.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @GetMapping("/info")
    public AppUserDto fetch(Authentication authentication){
        return appUserService.fetch(authentication);
    }
}
