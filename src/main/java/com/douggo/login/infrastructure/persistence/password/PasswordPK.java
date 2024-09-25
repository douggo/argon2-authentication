package com.douggo.login.infrastructure.persistence.password;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PasswordPK implements Serializable {

    private UUID userId;
    private String password;
    private LocalDateTime createdAt;

    public PasswordPK(UUID userId, String password, LocalDateTime createdAt) {
        this.userId = userId;
        this.password = password;
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "PasswordPK{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordPK that = (PasswordPK) o;
        return Objects.equals(userId, that.userId) && Objects.equals(password, that.password) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, createdAt);
    }
}
