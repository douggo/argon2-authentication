package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import com.douggo.login.infrastructure.persistence.user.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserGatewayJPA implements UserGateway {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserGatewayJPA(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<User> getAll() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public User getUserByEmail(String email) throws IllegalAccessException {
        Optional<UserEntity> user = this.repository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalAccessException("An error occured while validating user's data");
        }
        return this.mapper.toDomain(user.get());
    }

    @Override
    public User register(User user) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity userEntity = this.mapper.toEntity(user);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
        try {
            this.repository.save(userEntity);
        } catch(DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Address already in use!");
        }
        return this.mapper.toDomain(userEntity);
    }
}
