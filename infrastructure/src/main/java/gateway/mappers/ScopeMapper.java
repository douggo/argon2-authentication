package gateway.mappers;

import entity.Scope;
import exceptions.ObjectIsNullException;
import persistence.scope.ScopeEntity;

import java.util.Objects;

public class ScopeMapper {

    public ScopeEntity toEntity(Scope scope) {
        if (Objects.isNull(scope)) {
            throw new ObjectIsNullException(Scope.class.getSimpleName());
        }
        return ScopeEntity.toEntity(scope);
    }

    public Scope toDomain(ScopeEntity scope) {
        if (Objects.isNull(scope)) {
            throw new ObjectIsNullException(ScopeEntity.class.getSimpleName());
        }
        return ScopeEntity.toDomain(scope);
    }

}
