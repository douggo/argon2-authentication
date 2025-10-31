package gateway;

import entity.Password;
import entity.User;

import java.util.UUID;

public interface PasswordGateway {

    Password createPassword(User user, Password password);

    Password getUserPassword(UUID userId) throws IllegalAccessException;
}
