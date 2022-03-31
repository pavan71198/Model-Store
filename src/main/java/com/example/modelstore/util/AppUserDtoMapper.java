package com.example.modelstore.util;

import com.example.modelstore.dto.AppUserDto;
import com.example.modelstore.entity.AppUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserDtoMapper {
    public AppUserDto toAppUserDto(AppUser appUser){
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setId(appUser.getId().toString());
        appUserDto.setUsername(appUser.getUsername());
        appUserDto.setName(appUser.getName());
        return appUserDto;
    }

    public List<AppUserDto> toAppUserDto(List<AppUser> appUserList){
        List<AppUserDto> appUserDtoList = new ArrayList<>();
        for (AppUser appUser: appUserList){
            appUserDtoList.add(toAppUserDto(appUser));
        }
        return appUserDtoList;
    }
}
