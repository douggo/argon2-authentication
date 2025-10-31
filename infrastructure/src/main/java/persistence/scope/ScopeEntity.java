package persistence.scope;

import entity.Scope;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "scopes")
public class ScopeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    public ScopeEntity() {}

    public ScopeEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ScopeEntity of(Scope scope) {
        return new ScopeEntity(scope.getId(), scope.getName());
    }

    public static Scope toDomain(ScopeEntity scope) {
        return Scope.of(scope.getId(), scope.getName());
    }

    public static ScopeEntity toEntity(Scope scope) {
        return ScopeEntity.of(scope);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ScopeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScopeEntity that = (ScopeEntity) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
