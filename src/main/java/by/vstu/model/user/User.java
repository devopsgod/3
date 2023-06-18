package by.vstu.model.user;

import by.vstu.model.PersistentEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "users")
@TypeDefs({@TypeDef(name = "json", typeClass = JsonBinaryType.class)})
@AttributeOverride(name = "id", column = @Column(name = "u_id"))
public class User extends PersistentEntity implements UserDetails {

    @Column(name = "u_email", nullable = false, length = 70)
    private String email;

    @Column(name = "u_password", nullable = false, length = 80)
    private String password;

    @ManyToOne(optional = false)
    @JoinColumn(name = "r_id")
    private Role role;

    @Type(type = "json")
    @Column(name = "u_parameters", columnDefinition = "json")
    private Map<String, String> parameters;

    @Column(name = "u_locked")
    private Boolean locked = false;

    @Column(name = "u_deleted")
    private Boolean deleted = false;

    @Column(name = "u_activated")
    private Boolean activated = false;

    public User() {
    }

    public User(String email, String password, Role role) {
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_".concat(getRole().getName())));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !locked && !deleted && activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return email.equals(user.email) &&
                password.equals(user.password) &&
                role.equals(user.role) &&
                Objects.equals(parameters, user.parameters) &&
                locked.equals(user.locked) &&
                deleted.equals(user.deleted) &&
                activated.equals(user.activated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, role, parameters, locked, deleted, activated);
    }
}
