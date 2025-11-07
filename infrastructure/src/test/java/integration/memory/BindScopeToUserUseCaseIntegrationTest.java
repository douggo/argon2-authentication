package integration.memory;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.gateway.Memory.PasswordGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.ScopeGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.UserGatewayMemory;
import com.douggo.login.infrastructure.gateway.Memory.UserScopeGatewayMemory;
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
import com.douggo.login.application.usecases.RegisterUserUseCase;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BindScopeToUserUseCaseIntegrationTest {

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
                new UserGatewayMemory(new UserMapper(), this.userRepository, Clock.system(ZoneId.of("America/Sao_Paulo"))),
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
