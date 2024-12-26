package com.douggo.login.infrastructure.persistence.tokenScope;

import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "token_scopes")
public class AuthorizationTokenScopeEntity {

    @EmbeddedId
    private AuthorizationTokenScopePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tokenId")
    @JoinColumn(name = "token_id", nullable = false)
    private AuthorizationTokenEntity token;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("scopeId")
    @JoinColumn(name = "scope_id", nullable = false)
    private ScopeEntity scope;

    public AuthorizationTokenScopeEntity() {}

    public AuthorizationTokenScopeEntity(AuthorizationTokenScopePK id, AuthorizationTokenEntity token, ScopeEntity scope) {
        this.id = id;
        this.token = token;
        this.scope = scope;
    }

    public static List<AuthorizationTokenScopeEntity> fromUserScopes(List<UserScopeEntity> userScopes, AuthorizationTokenEntity token) {
        LocalDateTime now = LocalDateTime.now();
        return userScopes.stream()
                .map(userScope -> new AuthorizationTokenScopeEntity(
                        new AuthorizationTokenScopePK(userScope.getId().getScopeId(), token.getId()),
                        token,
                        userScope.getScope())
                )
                .toList();
    }

    public AuthorizationTokenScopePK getId() {
        return id;
    }

    public void setId(AuthorizationTokenScopePK id) {
        this.id = id;
    }

    public AuthorizationTokenEntity getToken() {
        return token;
    }

    public void setToken(AuthorizationTokenEntity token) {
        this.token = token;
    }

    public ScopeEntity getScope() {
        return scope;
    }

    public void setScope(ScopeEntity scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "TokenScopeEntity{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationTokenScopeEntity that = (AuthorizationTokenScopeEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
