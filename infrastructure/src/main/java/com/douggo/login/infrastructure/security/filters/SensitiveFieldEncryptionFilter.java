package com.douggo.login.infrastructure.security.filters;

import com.douggo.login.infrastructure.gateway.Argon2.PasswordEncryptionGatewayArgon2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@Order(1)
public class SensitiveFieldEncryptionFilter extends OncePerRequestFilter {

    private final PasswordEncryptionGatewayArgon2 passwordEncryptionGatewayArgon2;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Set<String> sensitiveFields = Set.of(
            "password",
            "newPassword",
            "confirmPassword"
    );

    public SensitiveFieldEncryptionFilter(PasswordEncryptionGatewayArgon2 passwordEncryptionGatewayArgon2) {
        this.passwordEncryptionGatewayArgon2 = passwordEncryptionGatewayArgon2;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/auth/login".equalsIgnoreCase(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(request);
        String body = new String(wrappedRequest.getInputStream().readAllBytes(), request.getCharacterEncoding());
        JsonNode jsonNode = objectMapper.readTree(body);
        boolean modified = false;
        for (String field : sensitiveFields) {
            if (jsonNode.has(field)) {
                String plainValue = jsonNode.get(field).asText();
                String hashedValue = passwordEncryptionGatewayArgon2.hashPassword(plainValue);
                ((ObjectNode) jsonNode).put(field, hashedValue);
                modified = true;
            }
        }
        if (modified) {
            byte[] modifiedBody = objectMapper.writeValueAsBytes(jsonNode);
            wrappedRequest.updateBody(modifiedBody);
        }
        filterChain.doFilter(wrappedRequest, response);
    }
}
