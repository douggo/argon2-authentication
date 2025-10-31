package gateway.JPA;

import entity.AuthorizationToken;
import entity.User;
import gateway.AuthorizationTokenGateway;
import gateway.mappers.AuthorizationTokenMapper;
import persistence.authorizationToken.AuthorizationTokenEntity;
import persistence.authorizationToken.AuthorizationTokenRepository;
import persistence.tokenScope.AuthorizationTokenScopeEntity;
import persistence.tokenScope.AuthorizationTokenScopeRepository;
import persistence.userScope.UserScopeEntity;
import persistence.userScope.UserScopeRepository;
import security.exceptions.DataNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AuthorizationTokenGatewayJPA implements AuthorizationTokenGateway {

    private final AuthorizationTokenRepository repository;
    private final UserScopeRepository userScopeRepository;
    private final AuthorizationTokenScopeRepository authorizationTokenScopeRepository;
    private final AuthorizationTokenMapper mapper;

    public AuthorizationTokenGatewayJPA(
            AuthorizationTokenRepository repository,
            UserScopeRepository userScopeRepository,
            AuthorizationTokenScopeRepository authorizationTokenScopeRepository,
            AuthorizationTokenMapper mapper
    ) {
        this.repository = repository;
        this.userScopeRepository = userScopeRepository;
        this.authorizationTokenScopeRepository = authorizationTokenScopeRepository;
        this.mapper = mapper;
    }

    @Override
    public AuthorizationToken generateAuthorizationToken(User user) {
        List<UserScopeEntity> userScopes = this.userScopeRepository.findById_UserId(user.getId())
                .orElseThrow(() -> new DataNotFoundException("User doesn't have any scopes associated!"));
        LocalDateTime now = LocalDateTime.now();
        AuthorizationTokenEntity tokenCreated = this.repository.save(
                this.mapper.toEntity(AuthorizationToken.of(UUID.randomUUID(), user, now, now.plusMinutes(5)))
        );
        this.authorizationTokenScopeRepository.saveAll(AuthorizationTokenScopeEntity.fromUserScopes(userScopes, tokenCreated));

        return this.mapper.toDomain(tokenCreated);
    }

    @Override
    public boolean isTokenExpired(String token) {
        AuthorizationTokenEntity tokenEntity = this.repository.findById(UUID.fromString(token))
                .orElseThrow(() -> new DataNotFoundException("Token doesn't exists!"));
        return tokenEntity.getExpiredAt().isBefore(LocalDateTime.now());
    }

}
