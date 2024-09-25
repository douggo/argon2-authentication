package com.douggo.login.config;

import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.application.usecases.RegisterUserUseCase;
import com.douggo.login.infrastructure.gateway.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.gateway.UserGatewayJPA;
import com.douggo.login.infrastructure.persistence.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    PasswordEncryptionGatewayArgon2 createPasswordEncryptionGatewayArgon2() {
        return new PasswordEncryptionGatewayArgon2();
    }

    @Bean
    UserMapper createUserMapper() {
        return new UserMapper();
    }

    @Bean
    PasswordMapper createPasswordMapper(PasswordEncryptionGatewayArgon2 passwordEncryptionGatewayArgon2) {
        return new PasswordMapper(passwordEncryptionGatewayArgon2);
    }

    @Bean
    RegisterUserUseCase createRegisterUserUseCase(UserGateway userGateway, PasswordGateway passwordGateway) {
        return new RegisterUserUseCase(userGateway, passwordGateway);
    }

    @Bean
    UserGatewayJPA createUserUseCaseJPAImpl(UserRepository persistenceRepository, UserMapper mapper) {
        return new UserGatewayJPA(persistenceRepository, mapper);
    }

}
