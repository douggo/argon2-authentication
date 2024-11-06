package com.douggo.login.infrastructure.controller;

import com.douggo.login.domain.entity.User;

public record UserResponseDTO(String id, String name) {

    public static UserResponseDTO fromDomain(User user) {
        return new UserResponseDTO(user.getId().toString(), user.getName());
    }
}
