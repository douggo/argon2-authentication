package com.douggo.login.domain.security;

public interface PasswordEncoder {

    String hashPassword(String password);

}
