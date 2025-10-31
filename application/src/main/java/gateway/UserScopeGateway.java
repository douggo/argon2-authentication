package gateway;

import entity.User;

import java.util.UUID;

public interface UserScopeGateway {

    User bindScopeToUser(User userWithScope);

    User getAllScopesFromUser(UUID userId) throws IllegalAccessException;

}
