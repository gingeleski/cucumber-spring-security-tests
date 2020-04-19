package roombook.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import roombook.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);
}
