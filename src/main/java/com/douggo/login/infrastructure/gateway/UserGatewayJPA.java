package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import com.douggo.login.infrastructure.persistence.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class UserGatewayJPA implements UserGateway {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserGatewayJPA(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<User> listAll() {
        return List.of();
    }

    @Override
    public User register(User user) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity userEntity = this.mapper.toEntity(user);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
        this.repository.save(userEntity);
        return this.mapper.toDomain(userEntity);
    }
}
