package com.douggo.login.infrastructure.controller;

import com.douggo.login.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UserRequest(
        String name,
        String email,
        String password,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        String dateOfBirth
) {

    public User toDomain() {
        return new User.Builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .dateOfBirth(LocalDate.parse(this.dateOfBirth))
                .create();
    }

    public UserRequest fromDomain(User user) {
        return new UserRequest(user.getName(), user.getEmail(), null, user.getDateOfBirth().toString());
    }

    public UserRequest fromRequestWithPassword(UserRequest userRequest, String encryptedPassword) {
        return new UserRequest(userRequest.name(), userRequest.email(), encryptedPassword, userRequest.dateOfBirth());
    }

}