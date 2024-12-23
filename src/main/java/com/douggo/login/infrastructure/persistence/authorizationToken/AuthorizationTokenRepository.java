package com.douggo.login.infrastructure.persistence.authorizationToken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorizationTokenRepository extends JpaRepository<AuthorizationTokenEntity, UUID> {
}
