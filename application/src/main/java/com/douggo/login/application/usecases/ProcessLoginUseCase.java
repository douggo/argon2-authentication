package com.douggo.login.application.usecases;

import com.douggo.login.application.dto.AuthDataDto;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.application.gateway.*;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;

public class ProcessLoginUseCase {

    private final UserGateway userGateway;
    private final PasswordGateway passwordGateway;
    private final PasswordEncryptionGateway passwordEncryptionGateway;
    private final AuthorizationTokenGateway authorizationTokenGateway;
    private final SessionGateway sessionGateway;
    private final RefreshTokenGateway refreshTokenGateway;

    public ProcessLoginUseCase(
            UserGateway userGateway,
            PasswordGateway passwordGateway,
            PasswordEncryptionGateway passwordEncryptionGateway,
            AuthorizationTokenGateway authorizationTokenGateway,
            SessionGateway sessionGateway,
            RefreshTokenGateway refreshTokenGateway
    ) {
        this.userGateway = userGateway;
        this.passwordGateway = passwordGateway;
        this.passwordEncryptionGateway = passwordEncryptionGateway;
        this.authorizationTokenGateway = authorizationTokenGateway;
        this.sessionGateway = sessionGateway;
        this.refreshTokenGateway = refreshTokenGateway;
    }

    public AuthSuccessDto execute(AuthDataDto authDataDto) throws IllegalAccessException {
        Session session =  this.sessionGateway.createSession(this.getUserFrom(authDataDto));
        return AuthSuccessDto.from(
                this.authorizationTokenGateway.generateAuthorizationToken(session, session.getUser()),
                this.refreshTokenGateway.generateRefreshToken(session)
        );
    }

    private User getUserFrom(AuthDataDto authDataDto) throws IllegalAccessException {
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
        return user;
    }

}
