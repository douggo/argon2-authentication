package com.douggo.login.application.dto;

import com.douggo.login.domain.entity.Scope;

public class ScopeRequestDto {

    private String name;

    public ScopeRequestDto() {}

    public ScopeRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Scope toDomain() {
        return Scope.of(this.name);
    }

    @Override
    public String toString() {
        return "ScopeRequestDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
