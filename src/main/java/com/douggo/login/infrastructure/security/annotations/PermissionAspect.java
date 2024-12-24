package com.douggo.login.infrastructure.security.annotations;

import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.application.gateway.AuthorizationTokenScopeGateway;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class PermissionAspect {

    private final AuthorizationTokenScopeGateway gateway;
    private final AuthorizationTokenGateway authorizationTokenGateway;

    public PermissionAspect(AuthorizationTokenScopeGateway gateway,
                            AuthorizationTokenGateway authorizationTokenGateway) {
        this.gateway = gateway;
        this.authorizationTokenGateway = authorizationTokenGateway;
    }

    @Before("@annotation(requiredScopes)")
    public void checkPermission(RequiredScopes requiredScopes) {
        if (requiredScopes.ignoreValidation()) {
            return;
        }

        String[] scopes = requiredScopes.value();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            throw new RuntimeException("Request not found!");
        }

        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || token.isBlank()) {
            throw new RuntimeException("Token not found");
        }

        if (this.authorizationTokenGateway.isTokenExpired(token)) {
            throw new RuntimeException("Token expired. Please login again.");
        }

        if (!this.gateway.doesTokenHasAnyRequiredScope(token, scopes)) {
            throw new RuntimeException("You don't have permission to access this endpoint!");
        }
    }

}
