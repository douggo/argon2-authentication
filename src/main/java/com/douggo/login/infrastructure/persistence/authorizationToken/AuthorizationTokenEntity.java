package com.douggo.login.infrastructure.persistence.authorizationToken;

import com.douggo.login.domain.entity.AuthorizationToken;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class AuthorizationTokenEntity {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private LocalDateTime generatedAt;

    private LocalDateTime expiredAt;

    public AuthorizationTokenEntity() {}

    public AuthorizationTokenEntity(UUID id, UserEntity user, LocalDateTime generatedAt, LocalDateTime expiredAt) {
        this.id = id;
        this.user = user;
        this.generatedAt = generatedAt;
        this.expiredAt = expiredAt;
    }

    public static AuthorizationTokenEntity of(AuthorizationToken token, User user) {
        UserEntity userEntity = UserEntity.fromUserDomain(user);
        return new AuthorizationTokenEntity(
                token.getId(),
                userEntity,
                token.getGeneratedAt(),
                token.getExpiredAt()
        );
    }

    public static AuthorizationToken toDomain(AuthorizationTokenEntity token) {
        return AuthorizationToken.of(
                token.getId(),
                UserEntity.toDomain(token.getUser()),
                token.getGeneratedAt(),
                token.getExpiredAt()
        );
    }

    public static AuthorizationTokenEntity from(AuthorizationToken tokenDomain) {
        return AuthorizationTokenEntity.of(tokenDomain, tokenDomain.getUser());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return "AuthorizationTokenEntity{" +
                "id=" + id +
                ", user=" + user +
                ", generatedAt=" + generatedAt +
                ", expiredAt=" + expiredAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationTokenEntity that = (AuthorizationTokenEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(generatedAt, that.generatedAt) && Objects.equals(expiredAt, that.expiredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, generatedAt, expiredAt);
    }
}
