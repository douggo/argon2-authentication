package com.douggo.login.infrastructure.controller.auth;

import com.douggo.login.application.dto.AuthDataDto;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.application.usecases.ProcessLoginUseCase;
import com.douggo.login.infrastructure.security.annotations.RequiredScopes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class AuthController {

    private final ProcessLoginUseCase processLoginUseCase;

    public AuthController(ProcessLoginUseCase processLoginUseCase) {
        this.processLoginUseCase = processLoginUseCase;
    }

    @PostMapping
    @RequiredScopes(ignoreValidation = true)
    public ResponseEntity<AuthSuccessDto> login(@RequestBody AuthDataDto authDataDto) throws IllegalAccessException {
        return ResponseEntity.status(201)
                .body(this.processLoginUseCase.execute(authDataDto));
    }

}
