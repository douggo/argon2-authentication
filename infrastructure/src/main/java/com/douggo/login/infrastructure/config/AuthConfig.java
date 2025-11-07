package com.douggo.login.infrastructure.config;

import com.douggo.login.application.gateway.*;
import com.douggo.login.application.usecases.*;
import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.infrastructure.gateway.JPA.*;
import com.douggo.login.infrastructure.gateway.mappers.*;
import com.douggo.login.infrastructure.persistence.refreshToken.RefreshTokenRepository;
import com.douggo.login.infrastructure.persistence.session.SessionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenRepository;
import com.douggo.login.infrastructure.persistence.scope.ScopeRepository;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeRepository;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeRepository;

@Configuration
public class AuthConfig {

    @Bean
    AuthorizationTokenMapper createAuthorizationTokenMapper() {
        return new AuthorizationTokenMapper();
    }

    @Bean
    ScopeMapper createScopeMapper() {
        return new ScopeMapper();
    }

    @Bean
    UserScopeMapper createUserScopeMapper() {
        return new UserScopeMapper();
    }

    @Bean
    SessionMapper createSessionMapper() {
        return new SessionMapper();
    }

    @Bean
    RefreshTokenMapper createRefreshTokenMapper() {
        return new RefreshTokenMapper();
    }

    @Bean
    AuthorizationTokenGatewayJPA createAuthorizationTokenGatewayJPA(
            AuthorizationTokenRepository authorizationTokenRepository,
            UserScopeRepository userScopeRepository,
            AuthorizationTokenScopeRepository authorizationTokenScopeRepository,
            AuthorizationTokenMapper authorizationTokenMapper
    ) {
        return new AuthorizationTokenGatewayJPA(
                authorizationTokenRepository,
                userScopeRepository,
                authorizationTokenScopeRepository,
                authorizationTokenMapper
        );
    }

    @Bean
    ScopeGatewayJPA createScopeGatewayJPA(ScopeRepository repository, ScopeMapper mapper) {
        return new ScopeGatewayJPA(repository, mapper);
    }

    @Bean
    UserScopeGatewayJPA createUserScopeGatewayJPA(UserScopeRepository repository, UserScopeMapper mapper) {
        return new UserScopeGatewayJPA(repository, mapper);
    }

    @Bean
    AuthorizationTokenScopeGatewayJPA createAuthorizationTokenScopeGatewayJPA(AuthorizationTokenScopeRepository repository) {
        return new AuthorizationTokenScopeGatewayJPA(repository);
    }

    @Bean
    SessionGatewayJPA createSessionGatewayJPA(SessionRepository repository, SessionMapper mapper) {
        return new SessionGatewayJPA(repository, mapper);
    }

    @Bean
    RefreshTokenGatewayJPA createRefreshTokenGatewayJPA(RefreshTokenRepository repository, RefreshTokenMapper mapper) {
        return new RefreshTokenGatewayJPA(repository, mapper);
    }

    @Bean
    ProcessLoginUseCase createProcessLoginUseCase(
            UserGateway userGateway,
            PasswordGateway passwordGateway,
            PasswordEncryptionGateway passwordEncryptionGateway,
            AuthorizationTokenGateway authorizationTokenGateway,
            SessionGateway sessionGateway,
            RefreshTokenGateway refreshTokenGateway
    ) {
        return new ProcessLoginUseCase(
                userGateway,
                passwordGateway,
                passwordEncryptionGateway,
                authorizationTokenGateway,
                sessionGateway,
                refreshTokenGateway
        );
    }

    @Bean
    CreateScopeUseCase createCreateScopeUseCase(ScopeGateway scopeGateway) {
        return new CreateScopeUseCase(scopeGateway);
    }

    @Bean
    ListAllScopesUseCase createListAllScopesUseCase(ScopeGateway scopeGateway) {
        return new ListAllScopesUseCase(scopeGateway);
    }

    @Bean
    BindScopeToUserUseCase createBindScopeToUserUseCase(UserScopeGateway userScopeGateway) {
        return new BindScopeToUserUseCase(userScopeGateway);
    }

    @Bean
    ListAllUserScopesUseCase createListAllUserScopesUseCase(UserScopeGateway userScopeGateway) {
        return new ListAllUserScopesUseCase(userScopeGateway);
    }

}
