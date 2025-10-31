package persistence.tokenScope;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class AuthorizationTokenScopePK implements Serializable {

    private UUID tokenId;
    private Integer scopeId;

    public AuthorizationTokenScopePK() {}

    public AuthorizationTokenScopePK(Integer scopeId, UUID tokenId) {
        this.scopeId = scopeId;
        this.tokenId = tokenId;
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public void setTokenId(UUID tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getScopeId() {
        return scopeId;
    }

    public void setScopeId(Integer scopeId) {
        this.scopeId = scopeId;
    }

    @Override
    public String toString() {
        return "TokenScopePK{" +
                "tokenId=" + tokenId +
                ", scopeId=" + scopeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationTokenScopePK that = (AuthorizationTokenScopePK) o;
        return Objects.equals(tokenId, that.tokenId) && Objects.equals(scopeId, that.scopeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId, scopeId);
    }
}
