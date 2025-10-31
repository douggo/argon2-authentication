package entity;

import com.douggo.login.domain.entity.Scope;
import com.douggo.login.domain.exceptions.ScopeIdNotInformedException;
import com.douggo.login.domain.exceptions.ScopeNameNotInformedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScopeUnitTest {

    private final int id = 1;
    private final String name = "test.suite";

    @Test
    void shouldCreateScopeIfDataIsValidThroughStaticMethod() {
        Scope scope = Scope.of(id, name);
        assertEquals(id, scope.getId());
        assertEquals(name, scope.getName());
    }

    @Test
    void shouldCreateScopeWithOnlyIdIfDataIsValidThroughStaticMethod() {
        Scope scope = Scope.of(id);
        assertEquals(id, scope.getId());
        assertNull(scope.getName());
    }

    @Test
    void shouldCreateScopeWithOnlyNameIfDataIsValidThroughStaticMethod() {
        Scope scope = Scope.of(name);
        assertEquals(0, scope.getId());
        assertEquals(name, scope.getName());
    }

    @Test
    void shouldNotCreateScopeIfIdIsNull() {
        assertThrows(
                ScopeIdNotInformedException.class,
                () -> Scope.of(0, name)
        );
    }

    @Test
    void shouldNotCreateScopeIfNameIsNull() {
        assertThrows(
                ScopeNameNotInformedException.class,
                () -> Scope.of(id, null)
        );
    }

    @Test
    void shouldNotCreateScopeWithOnlyIdIfIdIsNull() {
        assertThrows(
                ScopeIdNotInformedException.class,
                () -> Scope.of(0)
        );
    }

    @Test
    void shouldNotCreateScopeWithOnlyNameIfNameIsNull() {
        assertThrows(
                ScopeNameNotInformedException.class,
                () -> Scope.of(null)
        );
    }

}
