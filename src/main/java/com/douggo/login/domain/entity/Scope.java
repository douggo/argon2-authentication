package com.douggo.login.domain.entity;

import java.util.Objects;

public class Scope {

    private int id;

    private String name;

    private Scope(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private Scope(int id) {
        this.id = id;
    }

    private Scope(String name) {
        this.name = name;
    }

    public static Scope of(int id, String name) {
        return new Scope(id, name);
    }

    public static Scope of(String name) {
        return new Scope(name);
    }

    public static Scope of(int id) {
        return new Scope(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "token=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scope scope = (Scope) o;
        return Objects.equals(id, scope.id) && Objects.equals(name, scope.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static class Validator {

        public static void validate(int id, String name) throws IllegalAccessException {
            validateId(id);
            validateName(name);
        }

        private static void validateId(int id) throws IllegalAccessException {
            if (id > 0) {
                return;
            }
            throw new IllegalArgumentException("ID must be informed!");
        }

        private static void validateName(String name) throws IllegalAccessException {
            if (!Objects.isNull(name)) {
                return;
            }
            throw new IllegalArgumentException("Scope must have a name!");
        }

    }

}
