package com.douggo.login.application.usecases;

import com.douggo.login.application.dto.AuthDataDto;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProcessLoginUseCase {

    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;
    private final PasswordEncryptionGateway passwordEncryptionGateway;

    public ProcessLoginUseCase(
            UserGateway userGateway,
            PasswordGateway passwordGateway,
            PasswordEncryptionGateway passwordEncryptionGateway
    ) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
        this.passwordEncryptionGateway = passwordEncryptionGateway;
    }

    public AuthSuccessDto execute(AuthDataDto authDataDto) throws IllegalAccessException {
        this.validateData(authDataDto);
        return AuthSuccessDto.of(UUID.randomUUID(), (LocalDateTime.now()).plusMinutes(5));
    }

    private void validateData(AuthDataDto authDataDto) throws IllegalAccessException {
        User user = this.userGateway.getUserByEmail(authDataDto.getEmail());
        Password password = this.passwordGateway.getUserPassword(user.getId());
        if (!this.passwordEncryptionGateway.isPasswordValid(password.getPassword(), authDataDto.getPassword())) {
            throw new IllegalAccessException("An error occured while validating user's data");
        }
    }

}
