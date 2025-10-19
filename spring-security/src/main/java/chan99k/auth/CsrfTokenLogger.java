package chan99k.auth;

import java.io.IOException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "filter", name = "csrf-logging.enabled", havingValue = "true")
public class CsrfTokenLogger implements Filter {

	@Override
	public void doFilter(
		ServletRequest servletRequest, ServletResponse servletResponse,
		FilterChain filterChain
	) throws IOException, ServletException {
		CsrfToken csrf = (CsrfToken)servletRequest.getAttribute("_csrf");
		log.info("csrf token : {}", csrf.getToken());
		filterChain.doFilter(servletRequest, servletResponse);
	}
}
