package persistence.scope;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<ScopeEntity, Integer> {

    Optional<ScopeEntity> findByName(String name);
}
