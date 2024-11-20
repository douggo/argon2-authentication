package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.PasswordEncryptionGateway;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncryptionGatewayArgon2 implements PasswordEncryptionGateway {

    private final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    private final String salt = "mesa_maestra@2024";

    @Override
    public String hashPassword(String password) {
        int iterations = 2;
        int memory = 65536;
        int parallelism = 1;
        return argon2.hash(iterations, memory, parallelism, (password + this.salt).toCharArray());
    }

    @Override
    public boolean isPasswordValid(String hashedPassword, String password) {
        return this.argon2.verify(hashedPassword, (password + this.salt).toCharArray());
    }
}
