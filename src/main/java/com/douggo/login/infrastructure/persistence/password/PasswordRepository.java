package com.douggo.login.infrastructure.persistence.password;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<PasswordEntity, PasswordPK> {}
