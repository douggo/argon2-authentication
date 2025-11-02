package com.douggo.login.application.usecases;

import com.douggo.login.application.dto.AuthDataDto;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;
import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;

public class ProcessLoginUseCase {

    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;
    private final PasswordEncryptionGateway passwordEncryptionGateway;
    private final AuthorizationTokenGateway authorizationTokenGateway;
    private User user;

    public ProcessLoginUseCase(
            UserGateway userGateway,
            PasswordGateway passwordGateway,
            PasswordEncryptionGateway passwordEncryptionGateway,
            AuthorizationTokenGateway authorizationTokenGateway
    ) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
        this.passwordEncryptionGateway = passwordEncryptionGateway;
        this.authorizationTokenGateway = authorizationTokenGateway;
    }

    public AuthSuccessDto execute(AuthDataDto authDataDto) throws IllegalAccessException {
        this.validateData(authDataDto);
        Session session =  this.sessionGateway.createSession(this.user);
        return AuthSuccessDto.from(
                this.authorizationTokenGateway.generateAuthorizationToken(session, this.user),
                this.refreshTokenGateway.generateRefreshToken(session)
        );
    }

    private void validateData(AuthDataDto authDataDto) throws IllegalAccessException {
        User user = null;
        try {
            user = this.userGateway.getUserByEmail(authDataDto.getEmail());
        } catch(RuntimeException exception) {
            throw new IllegalAccessException("An error occurred while validating user's data");
        }
        Password password = this.passwordGateway.getUserLatestPassword(user.getId());
        if (!this.passwordEncryptionGateway.isPasswordValid(password.getPassword(), authDataDto.getPassword())) {
            throw new IllegalAccessException("An error occurred while validating user's data");
        }
        this.user = user;
    }

}
