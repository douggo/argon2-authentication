package gateway;

public interface PasswordEncryptionGateway {

    String hashPassword(String password);

    boolean isPasswordValid(String hashedPassword, String password);

}
