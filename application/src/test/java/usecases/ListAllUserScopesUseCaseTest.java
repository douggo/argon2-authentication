package usecases;

import entity.Scope;
import entity.User;
import gateway.Argon2.PasswordEncryptionGatewayArgon2;
import gateway.Memory.PasswordGatewayMemory;
import gateway.Memory.ScopeGatewayMemory;
import gateway.Memory.UserGatewayMemory;
import gateway.Memory.UserScopeGatewayMemory;
import gateway.PasswordEncryptionGateway;
import gateway.PasswordGateway;
import gateway.UserGateway;
import gateway.UserScopeGateway;
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

public class ListAllUserScopesUseCaseTest {

    private final UserMapper userMapper = new UserMapper();
    private final PasswordMapper passwordMapper = new PasswordMapper();

    private List<UserEntity> userRepository;
    private List<PasswordEntity> passwordRepository;
    private List<ScopeEntity> scopeRepository;
    private List<UserScopeEntity> userScopeRepository;

    private UserGateway userGateway;
    private PasswordGateway passwordGateway;
    private PasswordEncryptionGateway passwordEncryptionGateway;
    private UserScopeGateway userScopeGateway;

    private ListAllUserScopesUseCase listAllUserScopesUseCase;

    @BeforeEach
    public void setup() {
        this.registerUser();
        this.createScope();
        this.bindScopeToUser();
        this.setupUseCase();
    }

    private void registerUser() {
        this.userRepository = new ArrayList<>();
        this.passwordRepository = new ArrayList<>();
        this.userGateway = new UserGatewayMemory(this.userMapper, this.userRepository);
        this.passwordEncryptionGateway = new PasswordEncryptionGatewayArgon2();
        this.passwordGateway = new PasswordGatewayMemory(this.passwordMapper, this.userMapper, passwordRepository);

        new RegisterUserUseCase(this.userGateway, this.passwordGateway)
                .execute(
                        new User.Builder()
                                .name("John Doe")
                                .email("john.doe@mail.com.br")
                                .dateOfBirth(LocalDate.now())
                                .password(this.passwordEncryptionGateway.hashPassword("123"))
                                .create()
                );
    }

    private void createScope() {
        this.scopeRepository = new ArrayList<>();
        new CreateScopeUseCase(new ScopeGatewayMemory(new ScopeMapper(), this.scopeRepository))
                .execute(Scope.of(1, "test.suite"));
    }

    private void bindScopeToUser() {
        this.userScopeRepository = new ArrayList<>();
        this.userScopeGateway = new UserScopeGatewayMemory(this.userScopeRepository, new UserScopeMapper());
        UserEntity userEntity = this.userRepository.getFirst();
        new BindScopeToUserUseCase(this.userScopeGateway)
                .execute(
                        new User.Builder()
                                .id(userEntity.getId())
                                .name(userEntity.getName())
                                .email(userEntity.getEmail())
                                .dateOfBirth(LocalDate.now())
                                .scopes(ScopeEntity.toDomain(this.scopeRepository.getFirst()))
                                .create()
                );
    }

    private void setupUseCase() {
        this.listAllUserScopesUseCase = new ListAllUserScopesUseCase(this.userScopeGateway);
    }

    @Test
    void shouldListAllScopesFromUser() {
        AtomicReference<User> success = new AtomicReference<>();
        success.set(null);
        Assertions.assertDoesNotThrow(() -> success.set(this.listAllUserScopesUseCase.execute(this.userRepository.getFirst().getId())));
        Assertions.assertNotEquals(null, success.get());
        Assertions.assertEquals(1, success.get().getScopes().size());
    }

    @Test
    void shouldListEmptyIfNoScopesAreFound() {
        this.userScopeRepository.clear();
        AtomicReference<User> success = new AtomicReference<>();
        Assertions.assertDoesNotThrow(() -> success.set(this.listAllUserScopesUseCase.execute(this.userRepository.getFirst().getId())));
        Assertions.assertEquals(0, success.get().getScopes().size());
    }

}
