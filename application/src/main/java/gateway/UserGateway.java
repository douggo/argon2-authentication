package gateway;

import entity.User;

import java.util.List;

public interface UserGateway {

    List<User> getAll();

    User getUserByEmail(String email) throws IllegalAccessException;

    User register(User user);

}
