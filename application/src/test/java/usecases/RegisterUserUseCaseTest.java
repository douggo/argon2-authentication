package usecases;

import entity.User;
import exceptions.ObjectIsNullException;
import gateway.Argon2.PasswordEncryptionGatewayArgon2;
import gateway.Memory.PasswordGatewayMemory;
import gateway.Memory.UserGatewayMemory;
import gateway.PasswordEncryptionGateway;
import gateway.PasswordGateway;
import gateway.UserGateway;
import gateway.mappers.PasswordMapper;
import gateway.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.password.PasswordEntity;
import persistence.user.UserEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegisterUserUseCaseTest {

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

    @Test
    void shouldNotCreateIfUserIsNull() {
        assertThrows(
                ObjectIsNullException.class,
                () -> this.registerUserUseCase.execute(null)
        );
    }


}
