package com.douggo.login.infrastructure.controller;

import com.douggo.login.application.usecases.RegisterUserUseCase;
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
    public void registerUser(@RequestBody UserRequest userRequest) {
        this.registerUserUseCase.execute(userRequest.toDomain());
    }

}
