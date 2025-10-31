package entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AuthorizationToken {

    private final UUID id;
    private final User user;
    private final LocalDateTime generatedAt;
    private final LocalDateTime expiredAt;
    private List<Scope> scopes;

    private AuthorizationToken(UUID id, User user, LocalDateTime generatedAt, LocalDateTime expiredAt, List<Scope> scopes) {
        this.id = id;
        this.user = user;
        this.generatedAt = generatedAt;
        this.expiredAt = expiredAt;
        this.scopes = scopes;
    }

    private AuthorizationToken(UUID id, User user, LocalDateTime generatedAt, LocalDateTime expiredAt) {
        this.id = id;
        this.user = user;
        this.generatedAt = generatedAt;
        this.expiredAt = expiredAt;
    }

    public static AuthorizationToken of(UUID token, User user, LocalDateTime generatedAt, LocalDateTime expiredAt) {
        return new AuthorizationToken(token, user, generatedAt, expiredAt);
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    @Override
    public String toString() {
        return "AuthorizationToken{" +
                "token=" + id +
                ", user=" + user +
                ", generatedAt=" + generatedAt +
                ", expiredAt=" + expiredAt +
                ", scopes=" + scopes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationToken that = (AuthorizationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(generatedAt, that.generatedAt) && Objects.equals(expiredAt, that.expiredAt) && Objects.equals(scopes, that.scopes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, generatedAt, expiredAt, scopes);
    }
}
