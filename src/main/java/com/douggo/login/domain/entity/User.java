package com.douggo.login.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class User {

    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDate dateOfBirth;
    private final List<Password> passwords;

    private User(UUID id, String name, String email, LocalDate dateOfBirth, List<Password> passwords) {
        Validator.validateData(name, email, dateOfBirth);
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.passwords = passwords;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<Password> getPasswords() {
        return Collections.unmodifiableList(this.passwords);
    }

    public void addNewPassword(String hashedPassword) {
        this.passwords.forEach(Password::inactivate);
        this.passwords.add(Password.of(hashedPassword, LocalDateTime.now(), true));
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", passwords=" + passwords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(dateOfBirth, user.dateOfBirth) && Objects.equals(passwords, user.passwords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, dateOfBirth, passwords);
    }

    public static class Builder {

        private UUID id;
        private String name;
        private String email;
        private LocalDate dateOfBirth;
        private List<Password> passwords = new ArrayList<>();

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder password(String password) {
            this.passwords.forEach(Password::inactivate);
            this.passwords.add(Password.of(password, LocalDateTime.now(), true));
            return this;
        }

        public Builder passwordAlreadyCreated(Password password) {
            this.passwords.forEach(Password::inactivate);
            this.passwords.add(password);
            return this;
        }

        public User create() {
            return new User(this.id, this.name, this.email, this.dateOfBirth, this.passwords);
        }

    }

    public static class Validator {

        public static void validateData(String name, String email, LocalDate dateOfBirth) {
            validateName(name);
            validateEmail(email);
            validateDateOfBirth(dateOfBirth);
        }

        private static void validateName(String name) throws IllegalArgumentException {
            if (!Objects.isNull(name) && name.matches("^[A-Z][a-zA-Z\\s]*$")) {
                return;
            }
            throw new IllegalArgumentException("Name must be informed and must not contain special characters!");
        }

        private static void validateEmail(String email) throws IllegalArgumentException {
            if (!Objects.isNull(email) && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return;
            }
            throw new IllegalArgumentException("Something is wrong with the e-mail - check again!");
        }

        private static void validateDateOfBirth(LocalDate dateOfBirth) throws IllegalArgumentException {
            if (!Objects.isNull(dateOfBirth)) {
                return;
            }
            throw new IllegalArgumentException("Date of Birth must be informed!");
        }

    }

}