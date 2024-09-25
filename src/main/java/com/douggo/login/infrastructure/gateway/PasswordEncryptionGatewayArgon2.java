package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;

public class PasswordEncryptionGatewayArgon2 implements PasswordEncryptionGateway {

    @Override
    public String hashPassword(String password) {
        // password hash must be done before the password reaches the controller
        return "";
    }

    @Override
    public boolean verifyPassword(String hashedPassword, String password) {
        return false;
    }
}
