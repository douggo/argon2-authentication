package gateway.mappers;

import entity.Password;
import exceptions.ObjectIsNullException;
import persistence.password.PasswordEntity;
import persistence.password.PasswordPK;
import persistence.user.UserEntity;

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

}
