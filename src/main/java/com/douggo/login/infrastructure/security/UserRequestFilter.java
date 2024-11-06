package com.douggo.login.infrastructure.security;

import com.douggo.login.infrastructure.controller.UserRequest;
import com.douggo.login.infrastructure.gateway.PasswordEncryptionGatewayArgon2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
        requestWrapper.setRequestBody(this.modifyBody(requestWrapper.getRequestBody()));
        chain.doFilter(requestWrapper, response);
    }

    private String modifyBody(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        UserRequest user = mapper.readValue(body, UserRequest.class);
        PasswordEncryptionGatewayArgon2 encryptionGatewayArgon2 = new PasswordEncryptionGatewayArgon2();
        UserRequest userWithEncryptedPassword = user.fromRequestWithPassword(
                user,
                encryptionGatewayArgon2.hashPassword(user.password())
        );

        return mapper.writeValueAsString(userWithEncryptedPassword);
    }
}
