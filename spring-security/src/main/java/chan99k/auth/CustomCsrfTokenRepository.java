package chan99k.auth;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import chan99k.repository.TokenJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CustomCsrfTokenRepository implements CsrfTokenRepository {
	private final TokenJpaRepository tokenJpaRepository;

	@Override
	public CsrfToken generateToken(HttpServletRequest request) {
		String uuid = UUID.randomUUID().toString();
		return new DefaultCsrfToken("X-CSRF_TOKEN", "_csrf", uuid);
	}

	@Override
	public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
		String identifier = request.getHeader("X-IDENTIFIER");
		Optional<Token> foundToken = tokenJpaRepository.findTokenByIdentifier(identifier);

		if (foundToken.isPresent()) {
			Token token = foundToken.get();
			token.updateToken(csrfToken.getToken());
		} else {
			Token token = new Token(identifier, csrfToken.getToken());
			tokenJpaRepository.save(token);
		}
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		String identifier = request.getHeader("X-IDENTIFIER");
		Optional<Token> foundToken = tokenJpaRepository.findTokenByIdentifier(identifier);

		if (foundToken.isPresent()) {
			Token token = foundToken.get();
			return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
		}

		return null;
	}
}
