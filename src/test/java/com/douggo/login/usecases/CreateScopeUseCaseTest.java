package com.douggo.login.usecases;

import com.douggo.login.application.gateway.ScopeGateway;
import com.douggo.login.application.usecases.CreateScopeUseCase;
import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.exceptions.ObjectIsNullException;
import com.douggo.login.infrastructure.gateway.Memory.ScopeGatewayMemory;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateScopeUseCaseTest {

    private CreateScopeUseCase createScopeUseCase;

    @BeforeEach
    public void setup() {
        List<ScopeEntity> repository = new ArrayList<>();
        ScopeMapper mapper = new ScopeMapper();
        ScopeGateway scopeGateway = new ScopeGatewayMemory(mapper, repository);
        this.createScopeUseCase = new CreateScopeUseCase(scopeGateway);
    }

    @Test
    public void shouldReturnScopeCreated() {
        Scope scopeCreated = this.createScopeUseCase.execute(Scope.of(1, "test.suite"));
        Assertions.assertEquals(1, scopeCreated.getId());
        Assertions.assertEquals("test.suite", scopeCreated.getName());
    }

    @Test
    public void shouldNotCreateScopeIfNull() {
        Assertions.assertThrows(
                ObjectIsNullException.class,
                () -> this.createScopeUseCase.execute(null)
        );
    }

}
