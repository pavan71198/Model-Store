package com.example.modelstore.util.auth;

import com.example.modelstore.dto.auth.RegisterCredentialsDto;
import com.example.modelstore.entity.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CredentialsDtoMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(CredentialsDtoMapper.class);

    public AppUser toAppUser(RegisterCredentialsDto registerCredentialsDto){
        log.info(registerCredentialsDto.toString());
        AppUser appUser = new AppUser();
        appUser.setUsername(registerCredentialsDto.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerCredentialsDto.getPassword()));
        appUser.setName(registerCredentialsDto.getName());
        return appUser;
    }


}
