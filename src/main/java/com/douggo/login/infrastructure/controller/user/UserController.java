package com.douggo.login.infrastructure.controller.user;

import com.douggo.login.application.dto.UserResponseDto;
import com.douggo.login.application.usecases.ListAllUsersUseCase;
import com.douggo.login.application.usecases.RegisterUserUseCase;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.security.annotations.RequiredScopes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ListAllUsersUseCase listAllUsersUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase, ListAllUsersUseCase listAllUsersUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.listAllUsersUseCase = listAllUsersUseCase;
    }

    @PostMapping("/create")
    @RequiredScopes(ignoreValidation = false)
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequest userRequest) {
        User userCreated = this.registerUserUseCase.execute(userRequest.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponseDto.fromDomain(userCreated));
    }

    @GetMapping("/all")
    @RequiredScopes({"users.suite", "users.readonly"})
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> list = this.listAllUsersUseCase.execute()
                .stream()
                .map(UserResponseDto::fromDomain)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }

}
