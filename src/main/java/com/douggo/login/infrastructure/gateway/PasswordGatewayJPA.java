package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordRepository;

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
        PasswordEntity passwordEntity = PasswordEntity.toEntity(password, this.userMapper.toEntity(user));
        this.repository.save(passwordEntity);
    }

}
