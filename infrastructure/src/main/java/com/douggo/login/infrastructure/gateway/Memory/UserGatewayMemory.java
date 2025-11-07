package com.douggo.login.infrastructure.gateway.Memory;

import com.douggo.login.domain.entity.User;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import com.douggo.login.infrastructure.security.exceptions.DataNotFoundException;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserGatewayMemory implements UserGateway {

    private final UserMapper mapper;
    private final List<UserEntity> repository;
    private final Clock clock;

    public UserGatewayMemory(UserMapper mapper, List<UserEntity> repository, Clock clock) {
        this.mapper = mapper;
        this.repository = repository;
        this.clock = clock;
    }

    @Override
    public List<User> getAll() {
        return this.repository.stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public User getUserByEmail(String email) throws IllegalAccessException {
        return this.repository.stream()
                .filter(userEntity -> userEntity.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .map(this.mapper::toDomain)
                .orElseThrow(() -> new DataNotFoundException("An error occurred while validating user's data"));
    }

    @Override
    public User register(User user) {
        if (this.repository.stream().anyMatch(userEntity -> userEntity.getEmail().equalsIgnoreCase(user.getEmail()))) {
            throw new IllegalArgumentException("Address already in use!");
        }
        LocalDateTime now = LocalDateTime.now(this.clock);
        UserEntity userEntity = this.mapper.toEntity(user);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
        userEntity.setId(UUID.randomUUID());
        this.repository.add(userEntity);
        return this.mapper.toDomain(userEntity);
    }

    @Override
    public User getUserById(UUID id) throws IllegalAccessException {
        return null;
    }

}
