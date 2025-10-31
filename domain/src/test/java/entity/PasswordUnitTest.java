package entity;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.exceptions.PasswordCreateDateNotInformedException;
import com.douggo.login.domain.exceptions.PasswordNotHashedProperlyException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordUnitTest {

    private final String pass = "123";
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final boolean active = true;

    @Test
    void shouldCreatePasswordWithValidData() {
        Password password = Password.of(pass, createdAt, active);
        assertEquals(pass, password.getPassword());
        assertEquals(createdAt, password.getCreatedAt());
        assertEquals(active, password.isActive());
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
                () -> Password.of(pass, null, active)
        );
    }

}
