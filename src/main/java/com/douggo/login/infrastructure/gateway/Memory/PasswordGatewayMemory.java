package com.douggo.login.infrastructure.gateway.Memory;

import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class PasswordGatewayMemory implements PasswordGateway {

    private final PasswordMapper passwordMapper;
    private final UserMapper userMapper;
    private final List<PasswordEntity> repository;

    public PasswordGatewayMemory(PasswordMapper passwordMapper, UserMapper userMapper, List<PasswordEntity> repository) {
        this.passwordMapper = passwordMapper;
        this.userMapper = userMapper;
        this.repository = repository;
    }

    @Override
    public void createPassword(User user, Password password) {
        this.repository.add(this.passwordMapper.toEntity(password, this.userMapper.toEntity(user)));
    }

    @Override
    public Password getUserPassword(UUID userId) throws IllegalAccessException {
        Password password;
        try {
            password = this.repository.stream()
                    .filter(passwordEntity -> passwordEntity.getUserId().compareTo(userId) == 0)
                    .filter(PasswordEntity::isActive)
                    .map(this.passwordMapper::toDomain)
                    .toList()
                    .getFirst();
        } catch (NoSuchElementException e) {
            throw new IllegalAccessException("An error occurred while validating user's data");
        }
        return password;
    }

}
