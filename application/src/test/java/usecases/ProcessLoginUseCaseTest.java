package usecases;

import dto.AuthDataDto;
import dto.AuthSuccessDto;
import entity.Scope;
import entity.User;
import gateway.Argon2.PasswordEncryptionGatewayArgon2;
import gateway.AuthorizationTokenGateway;
import gateway.Memory.*;
import gateway.PasswordEncryptionGateway;
import gateway.PasswordGateway;
import gateway.UserGateway;
import gateway.mappers.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.authorizationToken.AuthorizationTokenEntity;
import persistence.password.PasswordEntity;
import persistence.scope.ScopeEntity;
import persistence.tokenScope.AuthorizationTokenScopeEntity;
import persistence.user.UserEntity;
import persistence.userScope.UserScopeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ProcessLoginUseCaseTest {

    private final UserMapper userMapper = new UserMapper();
    private final PasswordMapper passwordMapper = new PasswordMapper();
    private final AuthorizationTokenMapper authorizationTokenMapper = new AuthorizationTokenMapper();

    private List<UserEntity> userRepository;
    private List<PasswordEntity> passwordRepository;
    private List<ScopeEntity> scopeRepository;
    private List<UserScopeEntity> userScopeRepository;
    private List<AuthorizationTokenEntity> tokenRepository;
    private List<AuthorizationTokenScopeEntity> tokenScopeRepository;

    private UserGateway userGateway;
    private PasswordGateway passwordGateway;
    private PasswordEncryptionGateway passwordEncryptionGateway;
    private AuthorizationTokenGateway authorizationTokenGateway;

    private ProcessLoginUseCase processLoginUseCase;

    @BeforeEach
    public void setup() {
        this.registerUser();
        this.createScope();
        this.bindScopeToUser();
        this.setupProcessLoginUseCase();

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
        UserEntity userEntity = this.userRepository.getFirst();
        new BindScopeToUserUseCase(new UserScopeGatewayMemory(userScopeRepository, new UserScopeMapper()))
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

    private void setupProcessLoginUseCase() {
        this.tokenRepository = new ArrayList<>();
        this.tokenScopeRepository = new ArrayList<>();
        this.authorizationTokenGateway = new AuthorizationTokenGatewayMemory(
                this.tokenRepository,
                this.userScopeRepository,
                this.tokenScopeRepository,
                this.authorizationTokenMapper

        );
        this.processLoginUseCase = new ProcessLoginUseCase(
                this.userGateway,
                this.passwordGateway,
                this.passwordEncryptionGateway,
                this.authorizationTokenGateway
        );
    }

    @Test
    void ShouldProcessLoginIfDataIsCorrect() {
        AtomicReference<AuthSuccessDto> success = new AtomicReference<>();
        success.set(null);
        Assertions.assertDoesNotThrow(
                () -> success.set(this.processLoginUseCase.execute(new AuthDataDto("john.doe@mail.com.br", "123")))
        );
        Assertions.assertNotEquals(null, success.get());
    }

    @Test
    void shouldThrowExceptionIfPasswordIsWrong() {
        Assertions.assertThrows(
                IllegalAccessException.class,
                () -> this.processLoginUseCase.execute(new AuthDataDto("john.doe@mail.com.br", "321"))
        );
    }

    @Test
    void shouldThrowExceptionIfEmailDoesNotExists() {
        Assertions.assertThrows(
                IllegalAccessException.class,
                () -> this.processLoginUseCase.execute(new AuthDataDto("john@mail.com.br", "123"))
        );
    }

}
