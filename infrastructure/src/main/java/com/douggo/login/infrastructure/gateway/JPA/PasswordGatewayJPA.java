package com.douggo.login.infrastructure.gateway.JPA;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    private Password persist(User user, Password password) {
        return this.mapper.toDomain(
                this.repository.save(this.mapper.toEntity(password, this.userMapper.toEntity(user)))
        );
    }

    @Override
    public Password createPassword(User user, Password password) {
        return this.persist(user, password);
    }

    @Override
    public void updatePassword(User user, Password password) {
        this.persist(user, password);
    }

    @Override
    public Password getUserLatestPassword(UUID userId) throws IllegalAccessException {
        List<PasswordEntity> passwords = this.repository.findById_UserIdAndActiveTrue(userId)
                .orElseThrow(() -> new IllegalAccessException("An error occurred while validating user's data"));
        if (passwords.size() > 1) {
            throw new IllegalAccessException("An error occurred while validating user's data. Contact Support!");
        }
        return passwords
                .stream()
                .map(PasswordEntity::toDomain)
                .toList()
                .getFirst();
    }

    @Override
    public List<Password> getAllActivePasswordFromBefore(UUID userId, LocalDateTime before) throws IllegalAccessException {
        List<PasswordEntity> passwords = this.repository.findById_UserIdAndActiveTrueAndId_CreatedAtBefore(userId, before)
                .orElseThrow(() -> new IllegalAccessException("An error occurred while validating user's data"));
        return passwords.stream()
                .map(PasswordEntity::toDomain)
                .toList();
    }

}
