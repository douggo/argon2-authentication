package controller.configuration;

import dto.*;
import entity.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.annotations.RequiredScopes;
import usecases.BindScopeToUserUseCase;
import usecases.CreateScopeUseCase;
import usecases.ListAllScopesUseCase;
import usecases.ListAllUserScopesUseCase;

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
    @RequiredScopes("users.suite")
    public ResponseEntity<ScopeCreatedDto> createScopes(@RequestBody ScopeRequestDto scopeRequestDto) {
        Scope scopeCreated = this.createScopeUseCase.execute(scopeRequestDto.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ScopeCreatedDto.of(scopeCreated));
    }

    @GetMapping("/scope/all")
    @RequiredScopes({"users.suite", "users.readonly"})
    public ResponseEntity<List<ScopeCreatedDto>> getAllScopes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.listAllScopesUseCase.execute()
                        .stream()
                        .map(ScopeCreatedDto::of)
                        .toList()
                );
    }

    @PostMapping("/user-scope/{userId}")
    @RequiredScopes("users.suite")
    public ResponseEntity<UserScopeCreated> bindScopeIntoUser(
            @PathVariable("userId") UUID userId,
            @RequestBody UserScopeRequestDto userScopeRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserScopeCreated.from(this.bindScopeToUserUseCase.execute(userScopeRequestDto.toDomain(userId))));
    }

    @GetMapping("/user-scope/{userId}")
    @RequiredScopes({"users.suite", "users.readonly"})
    public  ResponseEntity<UserScopeList> getAllScopesFromUser(@PathVariable("userId") UUID userId) throws IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserScopeList.of(this.listAllUserScopesUseCase.execute(userId)));
    }

}
