package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PasswordGatewayJPA implements PasswordGateway {

    private final PasswordRepository repository;
    private final PasswordMapper mapper;
    private final UserMapper userMapper;

    public PasswordGatewayJPA(PasswordRepository repository, PasswordMapper mapper, UserMapper userMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @Override
    public void createPassword(User user, Password password) {
        PasswordEntity passwordEntity = this.mapper.toEntity(password, this.userMapper.toEntity(user));
        this.repository.save(passwordEntity);
    }

    @Override
    public Password getUserPassword(UUID userId) throws IllegalAccessException {
        Optional<List<PasswordEntity>> passwords = this.repository.findById_UserId(userId);
        if (passwords.isEmpty()) {
            throw new IllegalAccessException("An error occured while validating user's data");
        }
        return passwords.get()
                .stream()
                .filter(PasswordEntity::isActive)
                .map(PasswordEntity::toDomain)
                .toList()
                .getFirst();
    }

}
