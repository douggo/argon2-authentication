package com.douggo.login.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Session {

    private final UUID id;
    private final User user;
    private final LocalDateTime created_at;
    private final LocalDateTime expires_at;
    private final boolean revoked;

    private Session(UUID id, User user, LocalDateTime created_at, LocalDateTime expires_at, boolean revoked) {
        this.id = id;
        this.user = user;
        this.created_at = created_at;
        this.expires_at = expires_at;
        this.revoked = revoked;
    }

    public static Session of (UUID id, User user, LocalDateTime created_at, LocalDateTime expires_at, boolean revoked) {
        return new Session(id, user, created_at, expires_at, revoked);
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getExpires_at() {
        return expires_at;
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
                Objects.equals(created_at, session.created_at) &&
                Objects.equals(expires_at, session.expires_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, created_at, expires_at, revoked);
    }
}
