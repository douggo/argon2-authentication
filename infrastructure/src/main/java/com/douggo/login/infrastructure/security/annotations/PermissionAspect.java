package com.douggo.login.infrastructure.security.annotations;

import com.douggo.login.application.gateway.AuthorizationTokenGateway;
import com.douggo.login.application.gateway.AuthorizationTokenScopeGateway;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.douggo.login.infrastructure.security.exceptions.TokenAuthorizationException;

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
            throw new TokenAuthorizationException(HttpStatus.NOT_FOUND.value(), "Request not found!");
        }

        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || token.isBlank()) {
            throw new TokenAuthorizationException(HttpStatus.NOT_FOUND.value(), "Authorization header not found!");
        }

        if (this.authorizationTokenGateway.isTokenExpired(token)) {
            throw new TokenAuthorizationException(HttpStatus.UNAUTHORIZED.value(), "Token expired. Please login again to obtain a new one.");
        }

        if (!this.gateway.doesTokenHasAnyRequiredScope(token, scopes)) {
            throw new TokenAuthorizationException(HttpStatus.FORBIDDEN.value(), "You don't have permission to access this endpoint!");
        }
    }

}
