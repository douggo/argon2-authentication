package com.douggo.login.infrastructure.config;

import com.douggo.login.application.gateway.*;
import com.douggo.login.application.usecases.*;
import com.douggo.login.infrastructure.gateway.JPA.AuthorizationTokenGatewayJPA;
import com.douggo.login.infrastructure.gateway.JPA.AuthorizationTokenScopeGatewayJPA;
import com.douggo.login.infrastructure.gateway.JPA.ScopeGatewayJPA;
import com.douggo.login.infrastructure.gateway.JPA.UserScopeGatewayJPA;
import com.douggo.login.infrastructure.gateway.mappers.AuthorizationTokenMapper;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserScopeMapper;
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
    ProcessLoginUseCase createProcessLoginUseCase(
            UserGateway userGateway,
            PasswordGateway passwordGateway,
            PasswordEncryptionGateway passwordEncryptionGateway,
            AuthorizationTokenGateway authorizationTokenGateway
    ) {
        return new ProcessLoginUseCase(userGateway, passwordGateway, passwordEncryptionGateway, authorizationTokenGateway);
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
