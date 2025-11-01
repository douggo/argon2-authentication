package com.douggo.login.infrastructure.controller.user;

import com.douggo.login.application.dto.UserResponseDto;
import com.douggo.login.application.usecases.UpdateUserPasswordUseCase;
import com.douggo.login.application.command.UpdatePasswordCommand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.douggo.login.infrastructure.security.annotations.RequiredScopes;
import com.douggo.login.application.usecases.ListAllUsersUseCase;
import com.douggo.login.application.usecases.RegisterUserUseCase;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ListAllUsersUseCase listAllUsersUseCase;
    private final UpdateUserPasswordUseCase updateUserPasswordUseCase;

    public UserController(
            RegisterUserUseCase registerUserUseCase,
            ListAllUsersUseCase listAllUsersUseCase,
            UpdateUserPasswordUseCase updateUserPasswordUseCase
    ) {
        this.registerUserUseCase = registerUserUseCase;
        this.listAllUsersUseCase = listAllUsersUseCase;
        this.updateUserPasswordUseCase = updateUserPasswordUseCase;
    }

    @PostMapping("/create")
    @RequiredScopes(ignoreValidation = true)
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponseDto.fromDomain(this.registerUserUseCase.execute(userRequest.toDomain())));
    }

    @GetMapping("/all")
    @RequiredScopes({"users.suite", "users.readonly"})
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.listAllUsersUseCase.execute()
                        .stream()
                        .map(UserResponseDto::fromDomain)
                        .toList());
    }

    @PostMapping("/{userId}/password")
    @RequiredScopes("users.suite")
    public ResponseEntity<Void> updatePassword(
            @PathVariable UUID userId,
            @RequestBody PasswordUpdateRequest passwordUpdateRequest) throws IllegalAccessException {
        UpdatePasswordCommand command = new UpdatePasswordCommand(userId, passwordUpdateRequest.password());
        this.updateUserPasswordUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

}
