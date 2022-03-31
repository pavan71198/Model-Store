package com.example.modelstore.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginCredentialsDto {
    private String username;
    private String password;
}