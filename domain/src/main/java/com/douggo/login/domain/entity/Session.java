package com.douggo.login.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Session {

    private final UUID id;
    private final User user;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiresAt;
    private final boolean revoked;

    private Session(UUID id, User user, LocalDateTime createdAt, LocalDateTime expiresAt, boolean revoked) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
    }

    public static Session of (UUID id, User user, LocalDateTime createdAt, LocalDateTime expiresAt, boolean revoked) {
        return new Session(id, user, createdAt, expiresAt, revoked);
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return revoked == session.revoked &&
                Objects.equals(id, session.id) &&
                Objects.equals(user, session.user) &&
                Objects.equals(createdAt, session.createdAt) &&
                Objects.equals(expiresAt, session.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, createdAt, expiresAt, revoked);
    }
}
