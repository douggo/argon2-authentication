package com.douggo.login.infrastructure.security;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import com.douggo.login.domain.entity.Password;
import com.douggo.login.infrastructure.controller.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PasswordEncryptionInterceptor implements HandlerInterceptor {

    private final PasswordEncryptionGateway passwordEncryptionGateway;

    @Autowired
    public PasswordEncryptionInterceptor(PasswordEncryptionGateway passwordEncryptionGateway) {
        this.passwordEncryptionGateway = passwordEncryptionGateway;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!"POST".equalsIgnoreCase(request.getMethod()) && !request.getContentType().contains("application/json")) {
            return true;
        }

        String body = new BufferedReader(request.getReader()).lines().collect(Collectors.joining(System.lineSeparator()));
        UserRequest userRequest = new ObjectMapper().readValue(body, UserRequest.class);

        if (Objects.isNull(userRequest.password())) {
            // TO-DO: must throw an exception, because the password must be informed
            return true;
        }

        String encryptedPassword = this.passwordEncryptionGateway.hashPassword(userRequest.password());
        UserRequest updated = userRequest.fromRequestWithPassword(userRequest, encryptedPassword);

        String updatedBody = new ObjectMapper().writeValueAsString(updated);

        // TO-DO: create CustomHttpServletRequestWrapper to pass on the updated UserRequest with password encrypted

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
