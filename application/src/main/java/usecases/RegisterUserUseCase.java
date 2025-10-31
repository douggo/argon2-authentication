package usecases;

import entity.Password;
import entity.User;
import gateway.PasswordGateway;
import gateway.UserGateway;

public class RegisterUserUseCase {

    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public RegisterUserUseCase(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public User execute(User userDomain) {
        User userPersisted = this.userGateway.register(userDomain);
        Password passwordPersisted = this.passwordGateway.createPassword(userPersisted, userDomain.getPasswords().getFirst());
        return new User.Builder()
                .id(userPersisted.getId())
                .name(userPersisted.getName())
                .email(userPersisted.getEmail())
                .dateOfBirth(userPersisted.getDateOfBirth())
                .passwordAlreadyCreated(passwordPersisted)
                .create();
    }

}