package dto;

import entity.User;

public record UserResponseDto(String id, String name) {

    public static UserResponseDto fromDomain(User user) {
        return new UserResponseDto(user.getId().toString(), user.getName());
    }
}
