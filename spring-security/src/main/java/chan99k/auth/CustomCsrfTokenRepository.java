package chan99k.auth;

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
		return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
	}

	@Override
	public void saveToken(CsrfToken csrfToken, HttpServletRequest request, HttpServletResponse response) {
		if (csrfToken == null) {
			request.getSession().removeAttribute("CSRF_TOKEN");
			return;
		}
		String identifier = request.getHeader("X-IDENTIFIER");
		if (identifier != null) {
			tokenJpaRepository.findTokenByIdentifier(identifier)
				.ifPresentOrElse(
					existingToken -> existingToken.updateToken(csrfToken.getToken()),
					() -> tokenJpaRepository.save(new Token(identifier, csrfToken.getToken()))
				);
		}

		request.getSession().setAttribute("CSRF_TOKEN", csrfToken.getToken());
	}

	@Override
	public CsrfToken loadToken(HttpServletRequest request) {
		String identifier = request.getHeader("X-IDENTIFIER");
		if (identifier != null) {
			return tokenJpaRepository.findTokenByIdentifier(identifier)
				.map(token -> new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken()))
				.orElse(null);
		}

		String token = (String)request.getSession().getAttribute("CSRF_TOKEN");
		if (token != null) {
			return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
		}
		return null;
	}
}
