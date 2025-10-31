package controller.auth;

import dto.AuthDataDto;
import dto.AuthSuccessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.annotations.RequiredScopes;
import usecases.ProcessLoginUseCase;

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
