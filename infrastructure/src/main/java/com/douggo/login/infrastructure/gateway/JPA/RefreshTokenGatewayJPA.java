package com.douggo.login.infrastructure.gateway.JPA;

import com.douggo.login.application.gateway.RefreshTokenGateway;
import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.domain.entity.Session;
import com.douggo.login.infrastructure.gateway.mappers.RefreshTokenMapper;
import com.douggo.login.infrastructure.persistence.refreshToken.RefreshTokenEntity;
import com.douggo.login.infrastructure.persistence.refreshToken.RefreshTokenRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshTokenGatewayJPA implements RefreshTokenGateway {

    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;
    private final Clock clock;

    public RefreshTokenGatewayJPA(
            RefreshTokenRepository repository,
            RefreshTokenMapper mapper,
            Clock clock
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.clock = clock;
    }

    @Override
    public RefreshToken generateRefreshToken(Session session) {
        return this.mapper.toDomain(
                this.repository.save(
                        this.mapper.toEntity(
                                RefreshToken.of(
                                        UUID.randomUUID(),
                                        session,
                                        LocalDateTime.now(this.clock).plusMinutes(15),
                                        Boolean.FALSE,
                                        Boolean.FALSE
                                )
                        )
                )
        );
    }

    @Override
    public RefreshToken getFrom(UUID refreshToken) {
        return this.mapper.toDomain(this.repository.findById(refreshToken)
                .orElseThrow(() -> new IllegalAccessError("Error while validating data from request")));
    }

    @Override
    public void markAsUsed(RefreshToken refreshToken) {
        RefreshTokenEntity entity = this.mapper.toEntity(refreshToken);
        entity.setUsed(true);
        this.repository.save(entity);
    }
}
