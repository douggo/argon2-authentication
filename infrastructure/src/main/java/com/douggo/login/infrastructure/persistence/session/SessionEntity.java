package com.douggo.login.infrastructure.persistence.session;

import com.douggo.login.domain.entity.User;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class SessionEntity {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private final UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private final User user;

    private final LocalDateTime created_at;

    private final LocalDateTime expires_at;

    private final boolean revoked;

    public SessionEntity(UUID id, User user, LocalDateTime created_at, LocalDateTime expires_at, boolean revoked) {
        this.id = id;
        this.user = user;
        this.created_at = created_at;
        this.expires_at = expires_at;
        this.revoked = revoked;
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
        SessionEntity that = (SessionEntity) o;
        return revoked == that.revoked && Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(created_at, that.created_at) && Objects.equals(expires_at, that.expires_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, created_at, expires_at, revoked);
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id=" + id +
                ", user=" + user +
                ", created_at=" + created_at +
                ", expires_at=" + expires_at +
                ", revoked=" + revoked +
                '}';
    }
}
