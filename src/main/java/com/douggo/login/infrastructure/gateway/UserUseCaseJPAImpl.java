package com.douggo.login.infrastructure.gateway;

import com.douggo.login.application.gateway.UserUseCaseRepository;
import com.douggo.login.domain.entity.User;
import com.douggo.login.infrastructure.persistence.user.UserRepository;

import java.util.List;

public class UserUseCaseJPAImpl implements UserUseCaseRepository {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserUseCaseJPAImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<User> listAll() {
        return List.of();
    }

    @Override
    public User register(User user) {
        this.repository.save(this.mapper.toEntity(user));
        return user;
    }
}
