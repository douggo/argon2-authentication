package com.douggo.login.config;

import com.douggo.login.application.gateway.*;
import com.douggo.login.application.usecases.*;
import com.douggo.login.infrastructure.gateway.AuthorizationTokenGatewayJPA;
import com.douggo.login.infrastructure.gateway.ScopeGatewayJPA;
import com.douggo.login.infrastructure.gateway.UserScopeGatewayJPA;
import com.douggo.login.infrastructure.gateway.mappers.AuthorizationTokenMapper;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserScopeMapper;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenRepository;
import com.douggo.login.infrastructure.persistence.scope.ScopeRepository;
import com.douggo.login.infrastructure.persistence.tokenScope.AuthorizationTokenScopeRepository;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
