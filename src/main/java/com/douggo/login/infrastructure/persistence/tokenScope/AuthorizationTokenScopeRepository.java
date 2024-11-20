package com.douggo.login.infrastructure.persistence.tokenScope;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationTokenScopeRepository extends JpaRepository<AuthorizationTokenScopeEntity, AuthorizationTokenScopePK> {
}
