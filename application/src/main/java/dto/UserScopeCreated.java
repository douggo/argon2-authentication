package dto;

import entity.Scope;
import entity.User;

import java.util.UUID;

public class UserScopeCreated {

    private UUID userId;
    private Integer scopeId;
    private String scopeName;

    public UserScopeCreated() {}

    public UserScopeCreated(UUID userId, Integer scopeId, String scopeName) {
        this.userId = userId;
        this.scopeId = scopeId;
        this.scopeName = scopeName;
    }

    public static UserScopeCreated from(User user) {
        Scope scopeFromUser = user.getScopes().getFirst();
        return new UserScopeCreated(user.getId(), scopeFromUser.getId(), scopeFromUser.getName());
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(Integer scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public String toString() {
        return "UserScopeCreated{" +
                "userId=" + userId +
                ", scopeId=" + scopeId +
                ", scopeName='" + scopeName + '\'' +
                '}';
    }
}
