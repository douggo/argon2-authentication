package com.douggo.login.application.dto;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserScopeList {

    private UUID userId;
    private Map<Integer, String> scopes = new HashMap<>();

    public UserScopeList() {}

    public UserScopeList(UUID userId, Map<Integer, String> scopes) {
        this.userId = userId;
        this.scopes = scopes;
    }

    public UserScopeList(UUID userId, List<Scope> scopes) {
        this.userId = userId;
        this.addScopes(scopes);
    }

    public static UserScopeList of(UUID userId, List<Scope> scopes) {
        return new UserScopeList(userId, scopes);
    }

    public static UserScopeList of(User user) {
        return new UserScopeList(user.getId(), user.getScopes());
    }

    public UUID getUserId() {
        return userId;
    }

    public Map<Integer, String> getScopes() {
        return scopes;
    }

    public void addScopes(List<Scope> scopes) {
        scopes.forEach(scope -> this.scopes.put(scope.getId(), scope.getName()));
    }
}
