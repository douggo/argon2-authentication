package com.douggo.login.application.usecases;

import com.douggo.login.application.command.UpdatePasswordCommand;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateUserPasswordUseCase {

    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;

    public UpdateUserPasswordUseCase(UserGateway userGateway, PasswordGateway passwordGateway) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
    }

    public void execute(UpdatePasswordCommand command) throws IllegalAccessException {
        User user = this.userGateway.getUserById(command.userId());
        Password createdPassword = this.createNewPassword(user, command.newPassword());
        this.inactivateOlderPassword(user, createdPassword.getCreatedAt());
    }

    private Password createNewPassword(User user, String newPassword) {
        return this.passwordGateway.createPassword(user, Password.of(newPassword, LocalDateTime.now(), true));
    }

    private void inactivateOlderPassword(User user, LocalDateTime beforeThan) throws IllegalAccessException {
        List<Password> userActivePasswordsFromBefore = this.passwordGateway.getAllActivePasswordFromBefore(
                user.getId(),
                beforeThan
        );
        userActivePasswordsFromBefore.forEach(password -> {
            password.inactivate();
            this.passwordGateway.updatePassword(user, password);
        });
    }

}
