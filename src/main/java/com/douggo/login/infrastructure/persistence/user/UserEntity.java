package com.douggo.login.infrastructure.persistence.user;

import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordEntity> passwords = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(UUID id, String name, String email, LocalDate dateOfBirth, List<PasswordEntity> passwords) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.passwords = passwords;
    }

    public UserEntity(String name, String email, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public static UserEntity of(User user) {
        return new UserEntity(user.getName(), user.getEmail(), user.getDateOfBirth());
    }

    public static UserEntity fromUserDomain(User user) {
        return UserEntity.of(user);
    }

    public static User toDomain(UserEntity user) {
        return new User.Builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .dateOfBirth(user.getDateOfBirth())
                .create();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
