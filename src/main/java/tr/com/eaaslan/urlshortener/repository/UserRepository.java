package tr.com.eaaslan.urlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.eaaslan.urlshortener.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);
}
