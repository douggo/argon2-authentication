package gateway.Memory;

import entity.Password;
import entity.User;
import gateway.PasswordGateway;
import gateway.mappers.PasswordMapper;
import gateway.mappers.UserMapper;
import persistence.password.PasswordEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class PasswordGatewayMemory implements PasswordGateway {

    private final PasswordMapper passwordMapper;
    private final UserMapper userMapper;
    private final List<PasswordEntity> repository;

    public PasswordGatewayMemory(PasswordMapper passwordMapper, UserMapper userMapper, List<PasswordEntity> repository) {
        this.passwordMapper = passwordMapper;
        this.userMapper = userMapper;
        this.repository = repository;
    }

    @Override
    public Password createPassword(User user, Password password) {
        PasswordEntity passwordEntity = this.passwordMapper.toEntity(password, this.userMapper.toEntity(user));
        this.repository.add(passwordEntity);
        return this.passwordMapper.toDomain(passwordEntity);
    }

    @Override
    public Password getUserPassword(UUID userId) throws IllegalAccessException {
        Password password;
        try {
            password = this.repository.stream()
                    .filter(passwordEntity -> passwordEntity.getUserId().compareTo(userId) == 0)
                    .filter(PasswordEntity::isActive)
                    .map(this.passwordMapper::toDomain)
                    .toList()
                    .getFirst();
        } catch (NoSuchElementException e) {
            throw new IllegalAccessException("An error occurred while validating user's data");
        }
        return password;
    }

}
