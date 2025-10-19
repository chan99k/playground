package chan99k.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import chan99k.filter.LoggingFilter;
import chan99k.filter.RequestValidationFilter;
import chan99k.filter.StaticKeyAuthenticationFilter;
import chan99k.filter.TestFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;
	private final TestFilter testFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
			.addFilterAfter(new LoggingFilter(), BasicAuthenticationFilter.class)
			.addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class)
			.addFilterAt(testFilter, BasicAuthenticationFilter.class)
			.authorizeHttpRequests(c -> c.anyRequest().permitAll());

		return httpSecurity.build();
	}
}
