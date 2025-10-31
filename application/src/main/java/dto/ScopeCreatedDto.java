package dto;

import entity.Scope;

public class ScopeCreatedDto {

    private final Integer id;

    private final String name;

    public ScopeCreatedDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ScopeCreatedDto of(Scope scope) {
        return new ScopeCreatedDto(scope.getId(), scope.getName());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
