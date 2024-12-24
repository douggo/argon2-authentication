package com.douggo.login.application.gateway;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AuthorizationTokenScopeGateway {

    UserDetails convertIntoUserDetails(UUID token) throws IllegalAccessException;

}
