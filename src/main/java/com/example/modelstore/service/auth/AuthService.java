package com.example.modelstore.service.auth;

import com.example.modelstore.dto.auth.LoginCredentialsDto;
import com.example.modelstore.dto.auth.RegisterCredentialsDto;

public interface AuthService {
    String register(RegisterCredentialsDto registerCredentialsDto);
    String login(LoginCredentialsDto loginCredentialsDto);
}
