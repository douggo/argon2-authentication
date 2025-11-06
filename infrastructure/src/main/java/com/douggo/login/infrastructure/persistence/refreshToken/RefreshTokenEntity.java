package com.douggo.login.infrastructure.persistence.refreshToken;

import com.douggo.login.domain.entity.RefreshToken;
import com.douggo.login.infrastructure.persistence.session.SessionEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private SessionEntity session;

    private LocalDateTime expiresAt;

    private boolean used;

    private boolean revoked;

    public RefreshTokenEntity() {}

    public RefreshTokenEntity(UUID id, SessionEntity session, LocalDateTime expiresAt, boolean used, boolean revoked) {
        this.id = id;
        this.session = session;
        this.expiresAt = expiresAt;
        this.used = used;
        this.revoked = revoked;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SessionEntity getSession() {
        return session;
    }

    public void setSession(SessionEntity session) {
        this.session = session;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RefreshTokenEntity that = (RefreshTokenEntity) o;
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
        return "RefreshTokenEntity{" +
                "id=" + id +
                ", session=" + session +
                ", expiresAt=" + expiresAt +
                ", used=" + used +
                ", revoked=" + revoked +
                '}';
    }
}
