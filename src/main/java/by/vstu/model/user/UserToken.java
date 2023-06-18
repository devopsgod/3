package by.vstu.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_token")
public class UserToken {

    @Id
    @Column(name = "ut_token")
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "u_id")
    private User user;

    @Column(name = "ut_created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "ut_expired", nullable = false)
    private LocalDateTime expired;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserToken)) return false;
        UserToken userToken = (UserToken) o;
        return token.equals(userToken.token) &&
                user.equals(userToken.user) &&
                created.equals(userToken.created) &&
                expired.equals(userToken.expired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, user, created, expired);
    }
}
