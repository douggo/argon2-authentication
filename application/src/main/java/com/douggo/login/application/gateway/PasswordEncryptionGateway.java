package com.douggo.login.application.gateway;

public interface PasswordEncryptionGateway {

    String hashPassword(String password);

    boolean isPasswordValid(String hashedPassword, String password);

}
