package by.vstu.model.user;

import by.vstu.model.PersistentEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@Entity
@AttributeOverride(name = "id", column = @Column(name = "r_id"))
public class Role extends PersistentEntity {

    @Column(name = "r_name", nullable = false, length = 50)
    private String name;

    @Column(name = "r_display_name", nullable = false, length = 50)
    private String displayName;

    @Column(name = "r_is_free_access", nullable = false)
    private Boolean freeAccess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return name.equals(role.name) &&
                displayName.equals(role.displayName) &&
                freeAccess.equals(role.freeAccess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, displayName, freeAccess);
    }
}
