package com.douggo.login.infrastructure.gateway.JPA;

import com.douggo.login.application.gateway.SessionGateway;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.gateway.mappers.SessionMapper;
import com.douggo.login.infrastructure.persistence.session.SessionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionGatewayJPA implements SessionGateway {

    private final SessionRepository repository;
    private final SessionMapper mapper;

    public SessionGatewayJPA(SessionRepository repository, SessionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Session createSession(User user) {
        LocalDateTime createdAt = LocalDateTime.now();
        return this.mapper.toDomain(this.repository.save(
                mapper.toEntity(
                        Session.of(
                                UUID.randomUUID(),
                                user,
                                createdAt,
                                createdAt.plusHours(12),
                                false
                        )
                )
        ));
    }
}
