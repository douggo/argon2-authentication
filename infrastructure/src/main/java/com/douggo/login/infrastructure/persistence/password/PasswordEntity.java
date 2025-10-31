package com.douggo.login.infrastructure.persistence.password;

import com.douggo.login.domain.entity.Password;
import jakarta.persistence.*;
import com.douggo.login.infrastructure.persistence.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_passwords")
public class PasswordEntity {

    @EmbeddedId
    private PasswordPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private boolean active;

    public PasswordEntity() {}

    public PasswordEntity(PasswordPK id, UserEntity user, boolean active) {
        this.id = id;
        this.user = user;
        this.active = active;
    }

    public Password toDomain() {
        return Password.of(this.id.getPassword(), this.id.getCreatedAt(), this.active);
    }

    public static PasswordEntity toEntity(Password password, UserEntity user) {
        PasswordPK id = new PasswordPK(user.getId(), password.getPassword(), password.getCreatedAt());
        return new PasswordEntity(id, user, password.isActive());
    }

    public PasswordPK getId() {
        return id;
    }

    public void setId(PasswordPK id) {
        this.id = id;
    }

    public UUID getUserId() {
        return this.id.getUserId();
    }

    public void setUserId(UUID userId) {
        this.id.setUserId(userId);
    }

    public String getPassword() {
        return this.id.getPassword();
    }

    public void setPassword(String password) {
        this.id.setPassword(password);
    }

    public LocalDateTime getCreatedAt() {
        return id.getCreatedAt();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.id.setCreatedAt(createdAt);
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
