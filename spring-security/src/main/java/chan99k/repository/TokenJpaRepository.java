package chan99k.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import chan99k.auth.Token;

public interface TokenJpaRepository extends JpaRepository<Token, Integer> {
	Optional<Token> findTokenByIdentifier(String identifier);
}
