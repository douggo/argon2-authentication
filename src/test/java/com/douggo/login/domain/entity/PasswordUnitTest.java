package com.douggo.login.domain.entity;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.domain.exceptions.PasswordCreateDateNotInformedException;
import com.douggo.login.domain.exceptions.PasswordNotHashedProperlyException;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordUnitTest {

    private final PasswordEncryptionGateway passwordEncryptionGateway = new PasswordEncryptionGatewayArgon2();
    private final String passwordHashed = this.passwordEncryptionGateway.hashPassword("123");
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final boolean active = true;

    @Test
    void shouldCreatePasswordWithValidData() {
        Password password = Password.of(passwordHashed, createdAt, active);
        assertEquals(passwordHashed, password.getPassword());
        assertEquals(createdAt, password.getCreatedAt());
        assertEquals(active, password.isActive());
    }

    @Test
    void shouldNotCreatePasswordIfIsNotHashed() {
        assertThrows(
                PasswordNotHashedProperlyException.class,
                () -> Password.of("123", createdAt, active)
        );
    }

    @Test
    void shouldNotCreatePasswordIfIsNull() {
        assertThrows(
                PasswordNotHashedProperlyException.class,
                () -> Password.of(null, createdAt, active)
        );
    }

    @Test
    void shouldNotCreatePasswordIfIsBlank() {
        assertThrows(
                PasswordNotHashedProperlyException.class,
                () -> Password.of("", createdAt, active)
        );
    }

    @Test
    void shouldNotCreatePasswordIfDoesntHaveCreationDate() {
        assertThrows(
                PasswordCreateDateNotInformedException.class,
                () -> Password.of(passwordHashed, null, active)
        );
    }

}
