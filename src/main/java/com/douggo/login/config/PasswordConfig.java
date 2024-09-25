package com.douggo.login.config;

import com.douggo.login.infrastructure.gateway.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.PasswordGatewayJPA;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.password.PasswordRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    PasswordGatewayJPA createPasswordGatewayJPA(
            PasswordRepository repository,
            PasswordMapper passwordMapper,
            UserMapper userMapper
    ) {
        return new PasswordGatewayJPA(repository, passwordMapper, userMapper);
    }

}
