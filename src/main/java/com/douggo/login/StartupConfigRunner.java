package com.douggo.login;

import com.douggo.login.application.usecases.BindScopeToUserUseCase;
import com.douggo.login.application.usecases.CreateScopeUseCase;
import com.douggo.login.application.usecases.RegisterUserUseCase;
import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.douggo.login.infrastructure.persistence.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StartupConfigRunner {

    private final RegisterUserUseCase registerUserUseCase;
    private final CreateScopeUseCase createScopeUseCase;
    private final BindScopeToUserUseCase bindScopeToUserUseCase;

    private final UserRepository userRepository;

    public StartupConfigRunner(
            RegisterUserUseCase registerUserUseCase,
            CreateScopeUseCase createScopeUseCase,
            BindScopeToUserUseCase bindScopeToUserUseCase,
            UserRepository userRepository
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.createScopeUseCase = createScopeUseCase;
        this.bindScopeToUserUseCase = bindScopeToUserUseCase;
        this.userRepository = userRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            String email = "admin@mail.com.br";
            if (this.userRepository.findByEmail(email).isEmpty()) {
                User admin = this.createAdminUser(email);
                Scope adminScope = this.createScope(1, "users.suite");
                this.createScope(2, "users.readonly");
                this.bindAdminScopeToAdmin(admin, adminScope);
            }
        };
    }

    private User createAdminUser(String email) {
        String password = new PasswordEncryptionGatewayArgon2().hashPassword("admin@123");
        return this.registerUserUseCase.execute(
                new User.Builder()
                    .name("Admin")
                    .email(email)
                    .dateOfBirth(LocalDate.now())
                    .password(password)
                    .create()
        );
    }

    private Scope createScope(int id, String scope) {
        return this.createScopeUseCase.execute(Scope.of(id, scope));
    }

    private void bindAdminScopeToAdmin(User admin, Scope adminScope) {
        this.bindScopeToUserUseCase.execute(new User.Builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .dateOfBirth(admin.getDateOfBirth())
                .passwordAlreadyCreated(admin.getPasswords().getFirst())
                .scopes(adminScope)
                .create()
        );
    }

}
