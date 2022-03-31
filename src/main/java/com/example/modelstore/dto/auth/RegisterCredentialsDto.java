package com.example.modelstore.dto.auth;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterCredentialsDto {
    private String username;
    private String password;
    private String name;
}
