package com.douggo.login.infrastructure.controller;

import com.douggo.login.application.usecases.RegisterUserUseCase;
import com.douggo.login.domain.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequest userRequest) {
        User userCreated = this.registerUserUseCase.execute(userRequest.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponseDTO.fromDomain(userCreated));
    }

}
