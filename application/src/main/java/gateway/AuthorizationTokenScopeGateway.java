package gateway;

public interface AuthorizationTokenScopeGateway {

    boolean doesTokenHasAnyRequiredScope(String token, String[] requiredScopes);
}
