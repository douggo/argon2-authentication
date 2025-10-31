package usecases;

import entity.User;
import gateway.UserGateway;

import java.util.List;

public class ListAllUsersUseCase {

    private final UserGateway userGateway;

    public ListAllUsersUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return this.userGateway.getAll();
    }

}
