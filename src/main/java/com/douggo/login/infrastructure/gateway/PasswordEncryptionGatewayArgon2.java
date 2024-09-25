package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;

public class PasswordEncryptionGatewayArgon2 implements PasswordEncryptionGateway {

    @Override
    public String hashPassword(String password) {
        // implementation
        return "";
    }

    @Override
    public boolean verifyPassword(String hashedPassword, String password) {
        return false;
    }
}
