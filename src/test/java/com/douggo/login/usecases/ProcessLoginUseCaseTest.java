package com.douggo.login.usecases;

import com.douggo.login.application.dto.AuthDataDto;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.application.usecases.ProcessLoginUseCase;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.Memory.AuthorizationTokenGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.PasswordGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.UserGatewayMemory;
import com.douggo.login.infrastructure.gateway.mappers.AuthorizationTokenMapper;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ProcessLoginUseCaseTest {

    private UserGateway userGateway;
    private PasswordGateway passwordGateway;
    private PasswordEncryptionGateway passwordEncryptionGateway;
    private AuthorizationTokenGateway authorizationTokenGateway;
    private ProcessLoginUseCase processLoginUseCase;

    @BeforeEach
    public void setup() {
        this.setupObjects();
        this.setupData();
    }

    private void setupData() {
        this.createDummyUser();
        this.createDummyScope();
        this.bindScopeToUser();
    }

    private void bindScopeToUser() {
    }

    private void setupObjects() {
        UserMapper userMapper = new UserMapper();
        this.userGateway = new UserGatewayMemory(userMapper, new ArrayList<>());
        this.passwordGateway = new PasswordGatewayMemory(new PasswordMapper(), userMapper, new ArrayList<>());
        this.passwordEncryptionGateway = new PasswordEncryptionGatewayArgon2();
        this.authorizationTokenGateway = new AuthorizationTokenGatewayMemory(
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new AuthorizationTokenMapper()
        );
        this.processLoginUseCase = new ProcessLoginUseCase(
                this.userGateway,
                this.passwordGateway,
                this.passwordEncryptionGateway,
                this.authorizationTokenGateway
        );
    }

    @Test
    void shouldExecuteIfDataIsCorrect() throws IllegalAccessException {
        AtomicReference<AuthSuccessDto> success = new AtomicReference<>();
        Assertions.assertDoesNotThrow(
                () -> success.set(this.processLoginUseCase.execute(new AuthDataDto("johnny.doe@mail.com", "123")))
        );
    }

    private void createDummyScope() {

    }

    private void createDummyUser() {
        User userCreated = this.userGateway.register(new User.Builder()
                .name("John Doe Dummy")
                .email("johnny.doe@mail.com")
                .dateOfBirth(LocalDate.now())
                .create());
        Password passwordCreated = this.passwordGateway.createPassword(
                userCreated,
                Password.of(
                        this.passwordEncryptionGateway.hashPassword("123"),
                        LocalDateTime.now(),
                        true
                )
        );

    }

}
