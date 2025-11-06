package com.douggo.login.infrastructure.persistence.session;

import com.douggo.login.domain.entity.Session;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;
import com.douggo.login.infrastructure.persistence.refreshToken.RefreshTokenEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private boolean revoked;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AuthorizationTokenEntity> authorizationTokens = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshTokens = new ArrayList<>();

    public SessionEntity() {}

    public SessionEntity(UUID id, User user, LocalDateTime createdAt, LocalDateTime expiresAt, boolean revoked,
                         List<AuthorizationTokenEntity> authorizationTokens) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.authorizationTokens = authorizationTokens;
    }

    public SessionEntity(UUID id, User user, LocalDateTime createdAt, LocalDateTime expiresAt, boolean revoked) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }

    public static SessionEntity fromDomain(Session session) {
        return new SessionEntity(
                session.getId(),
                session.getUser(),
                session.getCreatedAt(),
                session.getExpiresAt(),
                session.isRevoked()
        );
    }

    public static Session toDomain(SessionEntity entity) {
        return Session.of(
                entity.getId(),
                entity.getUser(),
                entity.getCreatedAt(),
                entity.getExpiresAt(),
                entity.isRevoked()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public List<AuthorizationTokenEntity> getAuthorizationTokens() {
        return authorizationTokens;
    }

    public void setAuthorizationTokens(List<AuthorizationTokenEntity> authorizationTokens) {
        this.authorizationTokens = authorizationTokens;
    }

    public List<RefreshTokenEntity> getRefreshTokens() {
        return refreshTokens;
    }

    public void setRefreshTokens(List<RefreshTokenEntity> refreshTokens) {
        this.refreshTokens = refreshTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SessionEntity that = (SessionEntity) o;
        return revoked == that.revoked &&
                Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, createdAt, expiresAt, revoked);
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id=" + id +
                ", user=" + user +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", revoked=" + revoked +
                '}';
    }
}
