package com.douggo.login.application.dto;

import com.douggo.login.domain.entity.User;

public record UserResponseDto(String id, String name) {

    public static UserResponseDto fromDomain(User user) {
        return new UserResponseDto(user.getId().toString(), user.getName());
    }
}
