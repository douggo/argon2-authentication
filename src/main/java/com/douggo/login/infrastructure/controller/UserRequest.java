package com.douggo.login.infrastructure.controller;

import com.douggo.login.domain.entity.User;

import java.time.LocalDate;

public record UserRequest(
        String name,
        String email,
        String password,
        LocalDate dateOfBirth
) {

    public User toDomain() {
        return new User.Builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .dateOfBirth(this.dateOfBirth)
                .create();
    }

    public UserRequest fromDomain(User user) {
        return new UserRequest(user.getName(), user.getEmail(), null, user.getDateOfBirth());
    }

    public UserRequest fromRequestWithPassword(UserRequest userRequest, String encryptedPassword) {
        return new UserRequest(userRequest.name(), userRequest.email(), encryptedPassword, userRequest.dateOfBirth());
    }

}