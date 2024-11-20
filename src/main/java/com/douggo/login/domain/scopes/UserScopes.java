package com.douggo.login.domain.scopes;

public enum UserScopes {

    USERS_READONLY("users.readonly"),
    USERS_SUITE("users.suite");

    private final String scope;

    UserScopes(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return this.scope;
    }


}
