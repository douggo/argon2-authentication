package gateway;

import entity.AuthorizationToken;
import entity.User;

public interface AuthorizationTokenGateway {

    AuthorizationToken generateAuthorizationToken(User user);

    boolean isTokenExpired(String token);
}
