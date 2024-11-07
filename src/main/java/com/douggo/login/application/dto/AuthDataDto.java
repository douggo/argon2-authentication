package com.douggo.login.application.dto;

public class AuthDataDto {

    private final String email;

    private final String password;

    public AuthDataDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
