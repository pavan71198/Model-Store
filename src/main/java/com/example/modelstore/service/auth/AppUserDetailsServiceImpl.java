package com.example.modelstore.service.auth;

import com.example.modelstore.entity.AppUser;
import com.example.modelstore.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Component
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UUID appUserId;
        try {
            appUserId = UUID.fromString(id);
        }
        catch (IllegalArgumentException exception){
            throw new UsernameNotFoundException("User with id: "+id+"not found");
        }
        Optional<AppUser> appUserMatch = appUserRepository.findById(appUserId);
        if (appUserMatch.isEmpty()){
            throw new UsernameNotFoundException("User with id as "+id+" not found");
        }
        AppUser appUser = appUserMatch.get();
        return new User(id, appUser.getPassword(), Collections.emptyList());
    }
}
