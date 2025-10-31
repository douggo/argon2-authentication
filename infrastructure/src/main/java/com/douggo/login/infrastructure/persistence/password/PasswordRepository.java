package com.douggo.login.infrastructure.persistence.password;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PasswordRepository extends JpaRepository<PasswordEntity, PasswordPK> {

    Optional<List<PasswordEntity>> findById_UserId(UUID userId);

}
