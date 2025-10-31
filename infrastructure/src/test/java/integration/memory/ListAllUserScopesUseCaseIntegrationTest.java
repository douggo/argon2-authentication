package integration.memory;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.Memory.PasswordGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.ScopeGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.UserGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.UserScopeGatewayMemory;
import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.application.gateway.PasswordGateway;
import com.douggo.login.application.gateway.UserGateway;
import com.douggo.login.application.gateway.UserScopeGateway;
import com.douggo.login.infrastructure.gateway.mappers.PasswordMapper;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserMapper;
import com.douggo.login.infrastructure.gateway.mappers.UserScopeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import com.douggo.login.application.usecases.BindScopeToUserUseCase;
import com.douggo.login.application.usecases.CreateScopeUseCase;
import com.douggo.login.application.usecases.ListAllUserScopesUseCase;
import com.douggo.login.application.usecases.RegisterUserUseCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ListAllUserScopesUseCaseIntegrationTest {

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
