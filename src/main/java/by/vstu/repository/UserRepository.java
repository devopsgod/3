package by.vstu.repository;

import by.vstu.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailAndDeletedFalse(String email);

    boolean existsByEmailAndIdNotAndDeletedFalse(String email, UUID id);

    Optional<User> findByEmailAndDeletedFalse(String email);

    @Modifying
    @Query(value = "UPDATE User u SET u.activated = true where u.email = ?1")
    void confirmUserEmail(String email);

}
