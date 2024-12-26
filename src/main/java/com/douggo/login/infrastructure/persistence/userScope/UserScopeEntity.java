package com.douggo.login.infrastructure.persistence.userScope;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.scope.ScopeEntity;
import com.douggo.login.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_scopes")
public class UserScopeEntity {

    @EmbeddedId
    private UserScopePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("scopeId")
    @JoinColumn(name = "scope_id", nullable = false)
    private ScopeEntity scope;

    public UserScopeEntity() {}

    public UserScopeEntity(UserScopePK id, UserEntity user, ScopeEntity scope) {
        this.id = id;
        this.user = user;
        this.scope = scope;
    }

    public static UserScopeEntity of(User userWithScope) {
        Scope scope = userWithScope.getScopes().getFirst();
        UserScopePK id = new UserScopePK(userWithScope.getId(), scope.getId());
        return new UserScopeEntity(id, UserEntity.fromUserDomain(userWithScope), ScopeEntity.of(scope));
    }

    public static User toDomain(UserScopeEntity userScopeCreated) {
        return new User.Builder()
                .id(userScopeCreated.getId().getUserId())
                .scopes(ScopeEntity.toDomain(userScopeCreated.getScope()))
                .createWithoutValidation();
    }

    public UserScopePK getId() {
        return id;
    }

    public void setId(UserScopePK id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ScopeEntity getScope() {
        return scope;
    }

    public void setScope(ScopeEntity scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "UserScopeEntity{" +
                "id=" + id +
                ", user=" + user +
                ", scope=" + scope +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserScopeEntity that = (UserScopeEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, scope);
    }
}
