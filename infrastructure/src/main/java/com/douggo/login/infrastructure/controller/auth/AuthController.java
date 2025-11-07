package com.douggo.login.infrastructure.controller.auth;

import com.douggo.login.application.command.RefreshAuthenticationCommand;
import com.douggo.login.application.dto.AuthDataDto;
import com.douggo.login.application.dto.AuthSuccessDto;
import com.douggo.login.application.usecases.RefreshAuthenticationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.douggo.login.infrastructure.security.annotations.RequiredScopes;
import com.douggo.login.application.usecases.ProcessLoginUseCase;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ProcessLoginUseCase processLoginUseCase;
    private final RefreshAuthenticationUseCase refreshAuthenticationUseCase;

    public AuthController(
            ProcessLoginUseCase processLoginUseCase,
            RefreshAuthenticationUseCase refreshAuthenticationUseCase
    ) {
        this.processLoginUseCase = processLoginUseCase;
        this.refreshAuthenticationUseCase = refreshAuthenticationUseCase;
    }

    @PostMapping("/login")
    @RequiredScopes(ignoreValidation = true)
    public ResponseEntity<AuthSuccessDto> login(@RequestBody AuthDataDto authDataDto) throws IllegalAccessException {
        return ResponseEntity.status(201)
                .body(this.processLoginUseCase.execute(authDataDto));
    }

    @PostMapping("/refresh")
    @RequiredScopes(ignoreValidation = true)
    public ResponseEntity<AuthSuccessDto> refresh(@RequestBody RefreshAuthenticationCommand command) throws IllegalAccessException {
        return ResponseEntity.status(201)
                .body(this.refreshAuthenticationUseCase.execute(command));
    }

}
