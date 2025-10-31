package entity;

import exceptions.UserDateOfBirthNotInformedProperlyException;
import exceptions.UserEmailNotInformedProperlyException;
import exceptions.UserNameNotInformedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserUnitTest {

    private final String name = "Test User";
    private final String email = "test.user@mail.com.br";
    private final LocalDate dateOfBirth = LocalDate.of(1997, 3, 19);
    private String pass = "123";

    @Test
    void shouldNotCreateUserIfNameIsInvalid() {
        assertThrows(
                UserNameNotInformedException.class,
                () -> new User.Builder()
                        .name(null)
                        .email(email)
                        .dateOfBirth(dateOfBirth)
                        .password(pass)
                        .create()
        );
        assertThrows(
                UserNameNotInformedException.class,
                () -> new User.Builder()
                        .name("")
                        .email(email)
                        .dateOfBirth(dateOfBirth)
                        .password(pass)
                        .create()
        );
        assertThrows(
                UserNameNotInformedException.class,
                () -> new User.Builder()
                        .name("Test@123")
                        .email(email)
                        .dateOfBirth(dateOfBirth)
                        .password(pass)
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
                        .password(pass)
                        .create()
        );
        assertThrows(
                UserEmailNotInformedProperlyException.class,
                () -> new User.Builder()
                        .name(name)
                        .email("")
                        .dateOfBirth(dateOfBirth)
                        .password(pass)
                        .create()
        );
        assertThrows(
                UserEmailNotInformedProperlyException.class,
                () -> new User.Builder()
                        .name(name)
                        .email("test.user@mail")
                        .dateOfBirth(dateOfBirth)
                        .password(pass)
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
                        .password(pass)
                        .create()
        );
    }
}
