package com.douggo.login.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class RefreshToken {

    private final UUID id;

    private final Session session;

    private final LocalDateTime expiresAt;

    private final boolean used;

    private final boolean revoked;

    private RefreshToken(UUID id, Session session, LocalDateTime expiresAt, boolean used, boolean revoked) {
        this.id = id;
        this.session = session;
        this.expiresAt = expiresAt;
        this.used = used;
        this.revoked = revoked;
    }

    public UUID getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public static RefreshToken of(UUID id, Session session, LocalDateTime expiresAt, boolean used, boolean revoked) {
        return new RefreshToken(id, session, expiresAt, used, revoked);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return used == that.used &&
                revoked == that.revoked &&
                Objects.equals(id, that.id) &&
                Objects.equals(session, that.session) &&
                Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, session, expiresAt, used, revoked);
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", session=" + session +
                ", expiresAt=" + expiresAt +
                ", used=" + used +
                ", revoked=" + revoked +
                '}';
    }
}
