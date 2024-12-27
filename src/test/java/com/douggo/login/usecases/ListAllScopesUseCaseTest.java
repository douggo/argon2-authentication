package com.douggo.login.usecases;

import com.douggo.login.application.gateway.ScopeGateway;
import com.douggo.login.application.usecases.ListAllScopesUseCase;
import com.douggo.login.domain.entity.Scope;
import com.douggo.login.infrastructure.gateway.Memory.ScopeGatewayMemory;
import com.douggo.login.infrastructure.gateway.mappers.ScopeMapper;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ListAllScopesUseCaseTest {

    private ListAllScopesUseCase listAllScopesUseCase;
    private List<ScopeEntity> repository;

    @BeforeEach
    public void setup() {
        this.repository = new ArrayList<>();
        ScopeMapper mapper = new ScopeMapper();
        ScopeGateway scopeGateway = new ScopeGatewayMemory(mapper, this.repository);
        this.listAllScopesUseCase = new ListAllScopesUseCase(scopeGateway);
    }

    @Test
    void shouldReturnAllScopes() {
        this.createDummyScopes();
        AtomicInteger atomicInteger = new AtomicInteger();
        List<Scope> scopesList = this.listAllScopesUseCase.execute();
        Assertions.assertEquals(10, scopesList.size());
        scopesList.forEach(scope -> {
            Assertions.assertEquals(atomicInteger.get() + 1, scope.getId());
            Assertions.assertEquals("test.suite " + atomicInteger.get(), scope.getName());
            atomicInteger.getAndIncrement();
        });
    }

    @Test
    void shouldReturnEmptyListIfNoScopesAreCreated() {
        List<Scope> scopes = this.listAllScopesUseCase.execute();
        Assertions.assertEquals(0, scopes.size());
    }

    private void createDummyScopes() {
        for (int i = 0; i < 10; i++) {
            this.repository.add(new ScopeEntity(i+1, "test.suite " + i));
        }
    }

}
