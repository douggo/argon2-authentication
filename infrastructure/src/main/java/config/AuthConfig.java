package config;

import gateway.*;
import gateway.JPA.AuthorizationTokenGatewayJPA;
import gateway.JPA.AuthorizationTokenScopeGatewayJPA;
import gateway.JPA.ScopeGatewayJPA;
import gateway.JPA.UserScopeGatewayJPA;
import gateway.mappers.AuthorizationTokenMapper;
import gateway.mappers.ScopeMapper;
import gateway.mappers.UserScopeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.authorizationToken.AuthorizationTokenRepository;
import persistence.scope.ScopeRepository;
import persistence.tokenScope.AuthorizationTokenScopeRepository;
import persistence.userScope.UserScopeRepository;
import usecases.*;

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
