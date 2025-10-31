package gateway.JPA;

import gateway.AuthorizationTokenScopeGateway;
import persistence.tokenScope.AuthorizationTokenScopeEntity;
import persistence.tokenScope.AuthorizationTokenScopeRepository;
import security.exceptions.DataNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AuthorizationTokenScopeGatewayJPA implements AuthorizationTokenScopeGateway {

    private final AuthorizationTokenScopeRepository repository;

    public AuthorizationTokenScopeGatewayJPA(AuthorizationTokenScopeRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean doesTokenHasAnyRequiredScope(String token, String[] requiredScopes) {
        List<AuthorizationTokenScopeEntity> tokenScopes = this.repository.findById_TokenId(UUID.fromString(token))
                .orElseThrow(() -> new DataNotFoundException("Token doesn't have any scopes associated with!"));
        return Arrays.stream(requiredScopes)
                .anyMatch(requiredScope -> tokenScopes.stream()
                        .anyMatch(tokenScope -> tokenScope.getScope()
                                .getName()
                                .equalsIgnoreCase(requiredScope)
                        )
                );
    }
}
