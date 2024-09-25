package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordPK;
import com.douggo.login.infrastructure.persistence.user.UserEntity;

import java.time.LocalDateTime;

public class PasswordMapper {

    private final PasswordEncryptionGateway passwordEncryptionGateway;

    public PasswordMapper(PasswordEncryptionGateway passwordEncryptionGateway) {
        this.passwordEncryptionGateway = passwordEncryptionGateway;
    }

    public Password toDomain(String plainPassword) {
        final String hashedPassword = passwordEncryptionGateway.hashPassword(plainPassword);
        return Password.of(hashedPassword, LocalDateTime.now(), true);
    }

    public PasswordEntity toEntity(Password password, UserEntity user) {
        final PasswordPK id = new PasswordPK(user.getId(), password.getPassword(), password.getCreatedAt());
        return new PasswordEntity(id, user, password.isActive());
    }

}
