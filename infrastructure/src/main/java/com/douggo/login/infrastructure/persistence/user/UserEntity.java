package com.douggo.login.infrastructure.persistence.user;

import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.authorizationToken.AuthorizationTokenEntity;
import com.douggo.login.infrastructure.persistence.password.PasswordEntity;
import com.douggo.login.infrastructure.persistence.session.SessionEntity;
import com.douggo.login.infrastructure.persistence.userScope.UserScopeEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private LocalDate dateOfBirth;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PasswordEntity> passwords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserScopeEntity> scopes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AuthorizationTokenEntity> authorizationTokens = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SessionEntity> sessions = new ArrayList<>();

    public UserEntity() {}

    public UserEntity(
            UUID id, String name, String email,
            LocalDate dateOfBirth, LocalDateTime createdAt,
            LocalDateTime updatedAt, List<PasswordEntity> passwords,
            List<UserScopeEntity> scopes, List<AuthorizationTokenEntity> authorizationTokens,
            List<SessionEntity> sessions
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.passwords = passwords;
        this.scopes = scopes;
        this.authorizationTokens = authorizationTokens;
        this.sessions = sessions;
    }

    public UserEntity(UUID id, String name, String email, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public static UserEntity of(User user) {
        return new UserEntity(user.getId(), user.getName(), user.getEmail(), user.getDateOfBirth());
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PasswordEntity> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<PasswordEntity> passwords) {
        this.passwords = passwords;
    }

    public List<UserScopeEntity> getScopes() {
        return scopes;
    }

    public void setScopes(List<UserScopeEntity> scopes) {
        this.scopes = scopes;
    }

    public List<AuthorizationTokenEntity> getAuthorizationTokens() {
        return authorizationTokens;
    }

    public void setAuthorizationTokens(List<AuthorizationTokenEntity> authorizationTokens) {
        this.authorizationTokens = authorizationTokens;
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", passwords=" + passwords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(passwords, that.passwords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, dateOfBirth, createdAt, updatedAt, passwords);
    }
}
