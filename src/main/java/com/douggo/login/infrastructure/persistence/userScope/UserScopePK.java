package com.douggo.login.infrastructure.persistence.userScope;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class UserScopePK implements Serializable {

    private UUID userId;

    private Integer scopeId;

    public UserScopePK() {}

    public UserScopePK(UUID userId, Integer scopeId) {
        this.userId = userId;
        this.scopeId = scopeId;
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
}
