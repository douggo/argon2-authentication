package config;

import gateway.JPA.UserGatewayJPA;
import gateway.PasswordGateway;
import gateway.UserGateway;
import gateway.mappers.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.user.UserRepository;
import usecases.ListAllUsersUseCase;
import usecases.RegisterUserUseCase;

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
