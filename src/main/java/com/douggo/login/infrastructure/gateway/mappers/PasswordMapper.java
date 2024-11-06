package com.douggo.login.infrastructure.gateway.mappers;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordPK;
import com.douggo.login.infrastructure.persistence.user.UserEntity;

public class PasswordMapper {

    public Password toDomain(PasswordEntity password) {
        return Password.of(password.getPassword(), password.getCreatedAt(), password.isActive());
    }

    public PasswordEntity toEntity(Password password, UserEntity user) {
        final PasswordPK id = new PasswordPK(user.getId(), password.getPassword(), password.getCreatedAt());
        return new PasswordEntity(id, user, password.isActive());
    }

}
