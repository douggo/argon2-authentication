package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PasswordGateway {

    Password createPassword(User user, Password password);

    Password getUserLatestPassword(UUID userId) throws IllegalAccessException;

    void updatePassword(User user, Password password);

    List<Password> getAllActivePasswordFromBefore(UUID userId, LocalDateTime before) throws IllegalAccessException;
}
