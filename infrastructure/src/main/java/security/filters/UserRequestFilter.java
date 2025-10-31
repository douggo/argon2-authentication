package security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.user.UserRequest;
import gateway.Argon2.PasswordEncryptionGatewayArgon2;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

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
