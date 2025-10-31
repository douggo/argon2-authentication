package dto;

import entity.Scope;
import entity.User;

import java.util.UUID;

public class UserScopeRequestDto {

    private Integer scopeId;

    public UserScopeRequestDto() {}

    public UserScopeRequestDto(Integer scopeId) {
        this.scopeId = scopeId;
    }

    public User toDomain(UUID userId) {
        return new User.Builder()
                .id(userId)
                .scopes(Scope.of(scopeId))
                .createWithoutValidation();
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(Integer scopeId) {
        this.scopeId = scopeId;
    }

    @Override
    public String toString() {
        return "UserScopeRequestDto{" +
                "scopeId=" + scopeId +
                '}';
    }
}
