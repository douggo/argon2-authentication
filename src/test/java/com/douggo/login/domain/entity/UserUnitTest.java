package com.douggo.login.domain.entity;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.domain.exceptions.UserDateOfBirthNotInformedProperlyException;
import com.douggo.login.domain.exceptions.UserEmailNotInformedProperlyException;
import com.douggo.login.domain.exceptions.UserNameNotInformedException;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserUnitTest {

    private final String name = "Test User";
    private final String email = "test.user@mail.com.br";
    private final LocalDate dateOfBirth = LocalDate.of(1997, 3, 19);
    private String hashedPassword;

    @BeforeEach
    public void setup() {
        PasswordEncryptionGateway passwordEncryptionGateway = new PasswordEncryptionGatewayArgon2();
        hashedPassword = passwordEncryptionGateway.hashPassword("123");
    }

    @Test
    void shouldNotCreateUserIfNameIsInvalid() {
        assertThrows(
                UserNameNotInformedException.class,
                () -> new User.Builder()
                        .name(null)
                        .email(email)
                        .dateOfBirth(dateOfBirth)
                        .password(hashedPassword)
                        .create()
        );
        assertThrows(
                UserNameNotInformedException.class,
                () -> new User.Builder()
                        .name("")
                        .email(email)
                        .dateOfBirth(dateOfBirth)
                        .password(hashedPassword)
                        .create()
        );
        assertThrows(
                UserNameNotInformedException.class,
                () -> new User.Builder()
                        .name("Test@123")
                        .email(email)
                        .dateOfBirth(dateOfBirth)
                        .password(hashedPassword)
                        .create()
        );
    }

    @Test
    void shouldNotCreateUserIfEmailIsInvalid() {
        assertThrows(
                UserEmailNotInformedProperlyException.class,
                () -> new User.Builder()
                        .name(name)
                        .email("test.user.com.br")
                        .dateOfBirth(dateOfBirth)
                        .password(hashedPassword)
                        .create()
        );
        assertThrows(
                UserEmailNotInformedProperlyException.class,
                () -> new User.Builder()
                        .name(name)
                        .email("")
                        .dateOfBirth(dateOfBirth)
                        .password(hashedPassword)
                        .create()
        );
        assertThrows(
                UserEmailNotInformedProperlyException.class,
                () -> new User.Builder()
                        .name(name)
                        .email("test.user@mail")
                        .dateOfBirth(dateOfBirth)
                        .password(hashedPassword)
                        .create()
        );
    }

    @Test
    void shouldNotCreateUserIfDateOfBirthIsInvalid() {
        assertThrows(
                UserDateOfBirthNotInformedProperlyException.class,
                () -> new User.Builder()
                        .name(name)
                        .email(email)
                        .dateOfBirth(null)
                        .password(hashedPassword)
                        .create()
        );
    }
}
