package com.douggo.login.infrastructure.controller;

import com.douggo.login.application.usecases.RegisterUser;
import com.douggo.login.domain.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RegisterUser registerUserUseCase;

    public UserController(RegisterUser registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping
    public UserDto registerUser(@RequestBody UserDto dto) {
        User user = this.registerUserUseCase.execute(dto.toDomain());
        return UserDto.fromDomain(user);
    }

}
