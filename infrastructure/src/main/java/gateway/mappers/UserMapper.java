package gateway.mappers;

import entity.User;
import exceptions.ObjectIsNullException;
import persistence.user.UserEntity;

import java.util.Objects;

public class UserMapper {

    public UserEntity toEntity(User user) {
        if (Objects.isNull(user)) {
            throw new ObjectIsNullException(User.class.getSimpleName());
        }
        return UserEntity.fromUserDomain(user);
    }

    public User toDomain(UserEntity user) {
        if (Objects.isNull(user)) {
            throw new ObjectIsNullException(UserEntity.class.getSimpleName());
        }
        return UserEntity.toDomain(user);
    }

}
