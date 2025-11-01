package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.exceptions.ObjectIsNullException;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordPK;
import com.douggo.login.infrastructure.persistence.user.UserEntity;

import java.time.LocalDateTime;
import java.util.Objects;

public class PasswordMapper {

    public Password toDomain(PasswordEntity password) {
        if (Objects.isNull(password)) {
            throw new ObjectIsNullException(PasswordEntity.class.getSimpleName());
        }
        return Password.of(password.getPassword(), password.getCreatedAt(), password.isActive());
    }

    public PasswordEntity toEntity(Password password, UserEntity user) {
        if (Objects.isNull(password)) {
            throw new ObjectIsNullException(Password.class.getSimpleName());
        }
        if (Objects.isNull(user)) {
            throw new ObjectIsNullException(UserEntity.class.getSimpleName());
        }
        final PasswordPK id = new PasswordPK(user.getId(), password.getPassword(), password.getCreatedAt());
        return new PasswordEntity(id, user, password.isActive());
    }

    public Password toDomain(String password) {
        return Password.of(password, LocalDateTime.now(), Boolean.TRUE);
    }
}
