package com.douggo.login.domain.entity;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private final String name;
    private final String email;
    private final LocalDate dateOfBirth;

    private User(String name, String email, LocalDate dateOfBirth) {
        Validator.validateData(name, email, dateOfBirth);
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    static public class Builder {
        private String name;
        private String email;
        private LocalDate dateOfBirth;

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

        public User create() {
            return new User(this.name, this.email, this.dateOfBirth);
        }
    }

    static class Validator {
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
