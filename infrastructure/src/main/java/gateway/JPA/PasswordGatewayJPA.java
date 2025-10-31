package gateway.JPA;

import entity.Password;
import entity.User;
import gateway.PasswordGateway;
import gateway.mappers.PasswordMapper;
import gateway.mappers.UserMapper;
import persistence.password.PasswordEntity;
import persistence.password.PasswordRepository;

import java.util.List;
import java.util.UUID;

public class PasswordGatewayJPA implements PasswordGateway {

    private final PasswordRepository repository;
    private final PasswordMapper mapper;
    private final UserMapper userMapper;

    public PasswordGatewayJPA(PasswordRepository repository, PasswordMapper mapper, UserMapper userMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.userMapper = userMapper;
    }

    @Override
    public Password createPassword(User user, Password password) {
        PasswordEntity passwordEntity = this.mapper.toEntity(password, this.userMapper.toEntity(user));
        return this.mapper.toDomain(this.repository.save(passwordEntity));
    }

    @Override
    public Password getUserPassword(UUID userId) throws IllegalAccessException {
        List<PasswordEntity> passwords = this.repository.findById_UserId(userId)
                .orElseThrow(() -> new IllegalAccessException("An error occurred while validating user's data"));
        return passwords
                .stream()
                .filter(PasswordEntity::isActive)
                .map(PasswordEntity::toDomain)
                .toList()
                .getFirst();
    }

}
