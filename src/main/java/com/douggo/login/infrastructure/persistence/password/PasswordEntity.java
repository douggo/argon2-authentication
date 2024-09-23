package com.douggo.login.infrastructure.persistence.password;

import com.douggo.login.infrastructure.persistence.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_passwords")
public class PasswordEntity {

    @EmbeddedId
    private PasswordPK id;

    @ManyToOne
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

    public PasswordPK getId() {
        return id;
    }

    public void setId(PasswordPK id) {
        this.id = id;
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
