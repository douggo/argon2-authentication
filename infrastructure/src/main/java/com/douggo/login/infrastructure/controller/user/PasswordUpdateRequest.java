package com.douggo.login.infrastructure.controller.user;

public record PasswordUpdateRequest(
        String password
) {

    public static PasswordUpdateRequest of(String encryptedPassword) {
        return new PasswordUpdateRequest(encryptedPassword);
    }

}
