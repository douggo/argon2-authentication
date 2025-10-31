package entity;

import exceptions.PasswordCreateDateNotInformedException;
import exceptions.PasswordNotHashedProperlyException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Password {

    private final String password;
    private final LocalDateTime createdAt;
    private boolean active;

    private Password(String password, LocalDateTime createdAt, boolean active) {
        Validator.validateData(password, createdAt);
        this.password = password;
        this.createdAt = createdAt;
        this.active = active;
    }

    public static Password of(String password, LocalDateTime createdAt, boolean active) {
        return new Password(password, createdAt, active);
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void inactivate() {
        this.active = false;
    }

    @Override
    public String toString() {
        return "Password{" +
                "password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return active == password.active && Objects.equals(this.password, password.password) && Objects.equals(createdAt, password.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, createdAt, active);
    }

    public static class Validator {
        public static void validateData(String password, LocalDateTime createdAt) {
            validatePassword(password);
            validateCreatedAt(createdAt);
        }

        private static void validatePassword(String password) throws IllegalArgumentException {
            if (!Objects.isNull(password) && !password.isBlank()) {
                return;
            }
            throw new PasswordNotHashedProperlyException("Password isn't hashed properly!");
        }

        private static void validateCreatedAt(LocalDateTime createdAt) throws IllegalArgumentException {
            if (!Objects.isNull(createdAt)) {
                return;
            }
            throw new PasswordCreateDateNotInformedException("Creation date must be informed!");
        }

    }

}
