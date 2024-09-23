package com.douggo.login.config;

import com.douggo.login.application.gateway.UserUseCaseRepository;
import com.douggo.login.application.usecases.RegisterUser;
import com.douggo.login.infrastructure.gateway.UserMapper;
import com.douggo.login.infrastructure.gateway.UserUseCaseJPAImpl;
import com.douggo.login.infrastructure.persistence.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    UserMapper createUserMapper() {
        return new UserMapper();
    }

    @Bean
    RegisterUser createRegisterUserUseCase(UserUseCaseRepository useCaseRepository) {
        return new RegisterUser(useCaseRepository);
    }

    @Bean
    UserUseCaseJPAImpl createUserUseCaseJPAImpl(UserRepository persistenceRepository, UserMapper mapper) {
        return new UserUseCaseJPAImpl(persistenceRepository, mapper);
    }

}
