package gateway;

import entity.Scope;

import java.util.List;

public interface ScopeGateway {

    Scope createScope(Scope scope);

    List<Scope> getAllScopes();
}
