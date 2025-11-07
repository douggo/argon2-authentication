package com.douggo.login.application.usecases;

import com.douggo.login.application.command.RefreshAuthenticationCommand;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.application.gateway.RefreshTokenGateway;
import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.domain.entity.Session;

import java.time.Clock;
import java.time.LocalDateTime;

public class RefreshAuthenticationUseCase {

    private final AuthorizationTokenGateway authorizationTokenGateway;
    private final RefreshTokenGateway refreshTokenGateway;
    private final Clock clock;

    public RefreshAuthenticationUseCase(
            AuthorizationTokenGateway authorizationTokenGateway,
            RefreshTokenGateway refreshTokenGateway,
            Clock clock
    ) {
        this.authorizationTokenGateway = authorizationTokenGateway;
        this.refreshTokenGateway = refreshTokenGateway;
        this.clock = clock;
    }

    public AuthSuccessDto execute(RefreshAuthenticationCommand command) {
        AuthorizationToken authToken = this.validateAuthTokenData(command);
        RefreshToken refreshToken = this.validateRefreshTokenData(command);

        if (authToken.getSession().getId().compareTo(refreshToken.getSession().getId()) != 0) {
            throw new IllegalStateException("Auth Token and Refresh Token must be from the same session!");
        }


        AuthorizationToken newAuthToken = this.authorizationTokenGateway.generateAuthorizationToken(
                refreshToken.getSession(),
                refreshToken.getSession().getUser()
        );

        RefreshToken newRefreshToken = this.refreshTokenGateway.generateRefreshToken(refreshToken.getSession());

        this.refreshTokenGateway.markAsUsed(refreshToken);

        return AuthSuccessDto.from(newAuthToken, newRefreshToken);
    }

    private RefreshToken validateRefreshTokenData(RefreshAuthenticationCommand command) {
        RefreshToken token = this.refreshTokenGateway.getFrom(command.getRefreshToken());

        if (token.getExpiresAt().isBefore(LocalDateTime.now(this.clock))) {
            throw new IllegalStateException("Refresh Token expired");
        }

        if (token.isUsed()) {
            throw new IllegalStateException("Refresh Token is already used");
        }

        if (token.isRevoked()) {
            throw new IllegalStateException("Refresh Token was revoked!");
        }

        Session session = token.getSession();

        if (session.getExpiresAt().isBefore(LocalDateTime.now(this.clock))) {
            throw new IllegalStateException("Session expired!");
        }

        if (session.isRevoked()) {
            throw new IllegalStateException("Session is not active anymore!");
        }

        return token;
    }

    private AuthorizationToken validateAuthTokenData(RefreshAuthenticationCommand command) {
        AuthorizationToken authToken = this.authorizationTokenGateway.getTokenFrom(command.getAuthorizationToken());

        if (authToken.getUser().getId().compareTo(command.getUserId()) != 0) {
            throw new IllegalStateException("There's been an error while validating data");
        }

        Session session = authToken.getSession();

        if (session.getExpiresAt().isBefore(LocalDateTime.now(this.clock))) {
            throw new IllegalStateException("Session expired!");
        }

        if (session.isRevoked()) {
            throw new IllegalStateException("Session is not active anymore!");
        }

        return authToken;
    }


}
