package persistence.userScope;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserScopeRepository extends JpaRepository<UserScopeEntity, UserScopePK> {

    Optional<List<UserScopeEntity>> findById_UserId(UUID id);
}
