package com.example.modelstore.controller.auth;

import com.example.modelstore.dto.auth.LoginCredentialsDto;
import com.example.modelstore.dto.auth.RegisterCredentialsDto;
import com.example.modelstore.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterCredentialsDto registerCredentialsDto) {
        return authService.register(registerCredentialsDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginCredentialsDto loginCredentialsDto) {
        return authService.login(loginCredentialsDto);
    }
}
