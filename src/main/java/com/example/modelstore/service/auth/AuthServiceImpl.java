package com.example.modelstore.service.auth;

import com.example.modelstore.dto.auth.LoginCredentialsDto;
import com.example.modelstore.dto.auth.RegisterCredentialsDto;
import com.example.modelstore.entity.AppUser;
import com.example.modelstore.repository.AppUserRepository;
import com.example.modelstore.util.auth.CredentialsDtoMapper;
import com.example.modelstore.util.auth.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CredentialsDtoMapper credentialsDtoMapper;

    @Autowired
    private JWTUtil jwtUtil;


    public String register(RegisterCredentialsDto registerCredentialsDto) throws ResponseStatusException {
        if (registerCredentialsDto.getUsername() == null || registerCredentialsDto.getPassword() == null || registerCredentialsDto.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Registration details incomplete" );
        }
        if (appUserRepository.existsByUsername(registerCredentialsDto.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with username: "+registerCredentialsDto.getUsername()+" already present.");
        }
        AppUser newAppUser = credentialsDtoMapper.toAppUser(registerCredentialsDto);
        appUserRepository.save(newAppUser);
        newAppUser = appUserRepository.save(newAppUser);
        return jwtUtil.generateToken(newAppUser.getId().toString());
    }

    public String login(LoginCredentialsDto loginCredentialsDto) throws ResponseStatusException {
        if (loginCredentialsDto.getUsername() == null || loginCredentialsDto.getPassword() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login credentials incomplete" );
        }
        Optional<AppUser> appUserMatch = appUserRepository.findByUsername(loginCredentialsDto.getUsername());
        if (appUserMatch.isEmpty()){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "User with username: "+loginCredentialsDto.getUsername()+" not present.");
        }
        AppUser appUser = appUserMatch.get();
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(appUser.getId().toString(), loginCredentialsDto.getPassword());
            authenticationManager.authenticate(authInputToken);
            return jwtUtil.generateToken(appUser.getId().toString());
        } catch (AuthenticationException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect login credentials");
        }
    }
}
