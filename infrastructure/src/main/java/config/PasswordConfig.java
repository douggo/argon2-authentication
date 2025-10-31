package config;

import gateway.Argon2.PasswordEncryptionGatewayArgon2;
import gateway.JPA.PasswordGatewayJPA;
import gateway.mappers.PasswordMapper;
import gateway.mappers.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import persistence.password.PasswordRepository;

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
