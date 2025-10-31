package com.douggo.login.infrastructure.config;

import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.JPA.PasswordGatewayJPA;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.douggo.login.infrastructure.persistence.password.PasswordRepository;

@Configuration
public class PasswordConfig {

    @Bean
    PasswordEncryptionGatewayArgon2 createPasswordEncryptionGatewayArgon2() {
        return new PasswordEncryptionGatewayArgon2();
    }

    @Bean
    PasswordMapper createPasswordMapper(PasswordEncryptionGatewayArgon2 passwordEncryptionGatewayArgon2) {
        return new PasswordMapper();
    }

    @Bean
    PasswordGatewayJPA createPasswordGatewayJPA(
            PasswordRepository repository,
            PasswordMapper passwordMapper,
            UserMapper userMapper
    ) {
        return new PasswordGatewayJPA(repository, passwordMapper, userMapper);
    }

}
