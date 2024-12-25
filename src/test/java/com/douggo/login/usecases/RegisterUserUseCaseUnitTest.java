package com.douggo.login.usecases;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.application.usecases.RegisterUserUseCase;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.Memory.PasswordGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.UserGatewayMemory;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterUserUseCaseUnitTest {

    private UserGateway userGateway;
    private RegisterUserUseCase registerUserUseCase;
    private String hashedPassword;

    @BeforeEach
    public void setup() {
        List<UserEntity> userRepository = new ArrayList<>();
        UserMapper userMapper = new UserMapper();
        this.userGateway = new UserGatewayMemory(userMapper, userRepository);

        List<PasswordEntity> passwordRepository = new ArrayList<>();
        PasswordMapper passwordMapper = new PasswordMapper();
        PasswordGateway passwordGateway = new PasswordGatewayMemory(passwordMapper, userMapper, passwordRepository);
        this.registerUserUseCase = new RegisterUserUseCase(this.userGateway, passwordGateway);

        PasswordEncryptionGateway passwordEncryptionGateway = new PasswordEncryptionGatewayArgon2();
        hashedPassword = passwordEncryptionGateway.hashPassword("123");
    }

    @Test
    void shouldInsertUserIfAllDataIsCorrectly() {
        String name = "Test User";
        String email = "test.user@mail.com.br";
        LocalDate dateOfBirth = LocalDate.of(1997, 3, 19);
        User user = new User.Builder()
                .name(name)
                .email(email)
                .dateOfBirth(dateOfBirth)
                .password(hashedPassword)
                .create();
        User userCreated = this.registerUserUseCase.execute(user);
        assertEquals(
                1,
                this.userGateway.getAll().size()
        );
        assertEquals(
                name,
                userCreated.getName()
        );
        assertEquals(
                email,
                userCreated.getEmail()
        );
        assertEquals(
                dateOfBirth,
                userCreated.getDateOfBirth())
        ;
        assertEquals(
                hashedPassword,
                userCreated.getPasswords().getFirst().getPassword()
        );
    }


}
