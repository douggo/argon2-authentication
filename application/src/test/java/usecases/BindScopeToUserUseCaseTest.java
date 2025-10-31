package usecases;

import entity.Scope;
import entity.User;
import gateway.Argon2.PasswordEncryptionGatewayArgon2;
import gateway.Memory.PasswordGatewayMemory;
import gateway.Memory.ScopeGatewayMemory;
import gateway.Memory.UserGatewayMemory;
import gateway.Memory.UserScopeGatewayMemory;
import gateway.mappers.PasswordMapper;
import gateway.mappers.ScopeMapper;
import gateway.mappers.UserMapper;
import gateway.mappers.UserScopeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.password.PasswordEntity;
import persistence.scope.ScopeEntity;
import persistence.user.UserEntity;
import persistence.userScope.UserScopeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BindScopeToUserUseCaseTest {

    private List<UserEntity> userRepository;
    private List<ScopeEntity> scopeRepository;
    private BindScopeToUserUseCase bindScopeToUserUseCase;

    @BeforeEach
    public void setup() {
        this.registerUser();
        this.createScope();
        List<UserScopeEntity> userScopeRepository = new ArrayList<>();
        this.bindScopeToUserUseCase = new BindScopeToUserUseCase(
                new UserScopeGatewayMemory(
                        userScopeRepository,
                        new UserScopeMapper()
                )
        );
    }

    private void registerUser() {
        this.userRepository = new ArrayList<>();
        List<PasswordEntity> passwordRepository = new ArrayList<>();
        new RegisterUserUseCase(
                new UserGatewayMemory(new UserMapper(), this.userRepository),
                new PasswordGatewayMemory(new PasswordMapper(), new UserMapper(), passwordRepository)
        ).execute(
                new User.Builder()
                        .name("John Doe")
                        .email("john.doe@mail.com.br")
                        .dateOfBirth(LocalDate.now())
                        .password(new PasswordEncryptionGatewayArgon2().hashPassword("123"))
                        .create()
        );
    }

    private void createScope() {
        this.scopeRepository = new ArrayList<>();
        new CreateScopeUseCase(new ScopeGatewayMemory(new ScopeMapper(), this.scopeRepository))
                .execute(Scope.of(1, "test.suite"));
    }

    @Test
    void shouldBindScopeToUser() {
        AtomicReference<User> success = new AtomicReference<>();
        success.set(null);
        Assertions.assertDoesNotThrow(() -> {
            UserEntity userEntity = this.userRepository.getFirst();
            success.set(this.bindScopeToUserUseCase.execute(
                    new User.Builder()
                            .id(userEntity.getId())
                            .name(userEntity.getName())
                            .email(userEntity.getEmail())
                            .dateOfBirth(LocalDate.now())
                            .scopes(ScopeEntity.toDomain(this.scopeRepository.getFirst()))
                            .create()
            ));
        });
        Assertions.assertEquals(1, success.get().getScopes().size());
    }

}
