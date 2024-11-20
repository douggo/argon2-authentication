package com.douggo.login.infrastructure.controller.configuration;

import com.douggo.login.application.dto.*;
import com.douggo.login.application.usecases.BindScopeToUserUseCase;
import com.douggo.login.application.usecases.CreateScopeUseCase;
import com.douggo.login.application.usecases.ListAllScopesUseCase;
import com.douggo.login.application.usecases.ListAllUserScopesUseCase;
import com.douggo.login.domain.entity.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private final CreateScopeUseCase createScopeUseCase;
    private final ListAllScopesUseCase listAllScopesUseCase;
    private final BindScopeToUserUseCase bindScopeToUserUseCase;
    private final ListAllUserScopesUseCase listAllUserScopesUseCase;

    public ConfigurationController(
            CreateScopeUseCase createScopeUseCase,
            ListAllScopesUseCase listAllScopesUseCase,
            BindScopeToUserUseCase bindScopeToUserUseCase,
            ListAllUserScopesUseCase listAllUserScopesUseCase
    ) {
        this.createScopeUseCase = createScopeUseCase;
        this.listAllScopesUseCase = listAllScopesUseCase;
        this.bindScopeToUserUseCase = bindScopeToUserUseCase;
        this.listAllUserScopesUseCase = listAllUserScopesUseCase;
    }

    @PostMapping("/scope")
    public ResponseEntity<ScopeCreatedDto> createScopes(@RequestBody ScopeRequestDto scopeRequestDto) {
        Scope scopeCreated = this.createScopeUseCase.execute(scopeRequestDto.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ScopeCreatedDto.of(scopeCreated));
    }

    @GetMapping("/scope/all")
    public ResponseEntity<List<ScopeCreatedDto>> getAllScopes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.listAllScopesUseCase.execute()
                        .stream()
                        .map(ScopeCreatedDto::of)
                        .toList()
                );
    }

    @PostMapping("/user-scope/{userId}")
    public ResponseEntity<UserScopeCreated> bindScopeIntoUser(
            @PathVariable("userId") UUID userId,
            @RequestBody UserScopeRequestDto userScopeRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserScopeCreated.from(this.bindScopeToUserUseCase.execute(userScopeRequestDto.toDomain(userId))));
    }

    @GetMapping("/user-scope/{userId}")
    public  ResponseEntity<UserScopeList> getAllScopesFromUser(@PathVariable("userId") UUID userId) throws IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserScopeList.of(this.listAllUserScopesUseCase.execute(userId)));
    }


}
