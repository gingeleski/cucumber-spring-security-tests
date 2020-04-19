package roombook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import roombook.model.user.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> { }
