package by.vstu.repository;

import by.vstu.model.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {

    Optional<UserToken> getByTokenAndExpiredAfter(String token, LocalDateTime date);

    Optional<UserToken> getByUserEmail(String email);
}
