package com.douggo.login.infrastructure.gateway.JPA;

import com.douggo.login.application.gateway.RefreshTokenGateway;
import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.infrastructure.gateway.mappers.RefreshTokenMapper;
import com.douggo.login.infrastructure.persistence.refreshToken.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshTokenGatewayJPA implements RefreshTokenGateway {

    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    public RefreshTokenGatewayJPA(RefreshTokenRepository repository, RefreshTokenMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public RefreshToken generateRefreshToken(Session session) {
        return this.mapper.toDomain(
                this.repository.save(
                        this.mapper.toEntity(
                                RefreshToken.of(
                                        UUID.randomUUID(),
                                        session,
                                        LocalDateTime.now().plusHours(2),
                                        false,
                                        false
                                )
                        )
                )
        );
    }
}
