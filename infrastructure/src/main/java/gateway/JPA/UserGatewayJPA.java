package gateway.JPA;

import entity.User;
import gateway.UserGateway;
import gateway.mappers.UserMapper;
import org.springframework.dao.DataIntegrityViolationException;
import persistence.user.UserEntity;
import persistence.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class UserGatewayJPA implements UserGateway {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserGatewayJPA(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<User> getAll() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::toDomain)
                .toList();
    }

    @Override
    public User getUserByEmail(String email) throws IllegalAccessException {
        UserEntity user = this.repository.findByEmail(email)
                .orElseThrow(() -> new IllegalAccessException("An error occurred while validating user's data"));
        return this.mapper.toDomain(user);
    }

    @Override
    public User register(User user) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity userEntity = this.mapper.toEntity(user);
        userEntity.setCreatedAt(now);
        userEntity.setUpdatedAt(now);
        try {
            this.repository.save(userEntity);
        } catch(DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Address already in use!");
        }
        return this.mapper.toDomain(userEntity);
    }
}
