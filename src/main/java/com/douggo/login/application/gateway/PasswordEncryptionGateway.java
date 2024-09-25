package com.douggo.login.application.gateway;

public interface PasswordEncryptionGateway {

    String hashPassword(String password);

    boolean verifyPassword(String hashedPassword, String password);

}