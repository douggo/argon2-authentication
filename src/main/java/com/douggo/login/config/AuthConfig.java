package com.douggo.login.config;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.application.usecases.ProcessLoginUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    ProcessLoginUseCase createProcessLoginUseCase(
            UserGateway userGateway,
            PasswordGateway passwordGateway,
            PasswordEncryptionGateway passwordEncryptionGateway
    ) {
        return new ProcessLoginUseCase(userGateway, passwordGateway, passwordEncryptionGateway);
    }

}
