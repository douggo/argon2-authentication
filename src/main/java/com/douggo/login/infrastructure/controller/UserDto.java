package com.douggo.login.infrastructure.controller;

import com.douggo.login.domain.entity.User;

import java.time.LocalDate;

public record UserDto(
        String name,
        String email,
        LocalDate dateOfBirth
) {

    public User toDomain() {
        return new User.Builder().name(this.name).email(this.email).dateOfBirth(this.dateOfBirth).create();
    }

    public static UserDto fromDomain(User user) {
        return new UserDto(user.getName(), user.getEmail(), user.getDateOfBirth());
    }

}