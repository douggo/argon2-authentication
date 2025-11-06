package com.douggo.login.infrastructure.persistence.session;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<SessionEntity, UUID> {
}
