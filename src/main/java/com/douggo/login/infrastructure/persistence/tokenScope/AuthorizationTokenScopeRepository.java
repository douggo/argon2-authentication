package com.douggo.login.infrastructure.persistence.tokenScope;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorizationTokenScopeRepository extends JpaRepository<AuthorizationTokenScopeEntity, AuthorizationTokenScopePK> {

    Optional<List<AuthorizationTokenScopeEntity>> findById_TokenId(UUID token);

}
