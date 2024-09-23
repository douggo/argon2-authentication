package com.douggo.login.domain.security;

public interface PasswordVerifier {

    boolean verifyPassword(String hashedPassword, String password);

}
