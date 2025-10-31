package com.douggo.login.infrastructure.config;

import com.douggo.login.infrastructure.gateway.JPA.UserGatewayJPA;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.douggo.login.infrastructure.persistence.user.UserRepository;
import com.douggo.login.application.usecases.ListAllUsersUseCase;
import com.douggo.login.application.usecases.RegisterUserUseCase;

@Configuration
public class UserConfig {

    @Bean
    UserMapper createUserMapper() {
        return new UserMapper();
    }

    @Bean
    RegisterUserUseCase createRegisterUserUseCase(UserGateway userGateway, PasswordGateway passwordGateway) {
        return new RegisterUserUseCase(userGateway, passwordGateway);
    }

    @Bean
    ListAllUsersUseCase listAllUsersUseCase(UserGateway userGateway) {
        return new ListAllUsersUseCase(userGateway);
    }

    @Bean
    UserGatewayJPA createUserGatewayJPA(UserRepository persistenceRepository, UserMapper mapper) {
        return new UserGatewayJPA(persistenceRepository, mapper);
    }

}
