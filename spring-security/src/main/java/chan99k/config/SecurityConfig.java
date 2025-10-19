package chan99k.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import chan99k.auth.CsrfTokenLogger;
import chan99k.auth.CustomCsrfTokenRepository;
import chan99k.filter.StaticKeyAuthenticationFilter;
import chan99k.filter.TestFilter;

@Configuration
public class SecurityConfig {

	private final StaticKeyAuthenticationFilter staticKeyAuthenticationFilter;
	private final TestFilter testFilter;
	private final CsrfTokenLogger csrfTokenLogger;
	private final CustomCsrfTokenRepository customCsrfTokenRepository;

	public SecurityConfig(
		@Autowired(required = false) StaticKeyAuthenticationFilter staticKeyAuthenticationFilter,
		@Autowired(required = false) TestFilter testFilter,
		@Autowired(required = false) CsrfTokenLogger csrfTokenLogger,
		CustomCsrfTokenRepository customCsrfTokenRepository
	) {
		this.staticKeyAuthenticationFilter = staticKeyAuthenticationFilter;
		this.testFilter = testFilter;
		this.csrfTokenLogger = csrfTokenLogger;
		this.customCsrfTokenRepository = customCsrfTokenRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public UserDetailsManager userDetailsManager() {
		UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager.createUser(User.withUsername("danny.kim").password("1q2w3e4r").build());
		return userDetailsManager;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// 커스텀 필터 설정
		httpSecurity
			// .addFilterAfter(new LoggingFilter(), BasicAuthenticationFilter.class)
			.authorizeHttpRequests(c -> c.anyRequest().permitAll())
			.addFilterAfter(csrfTokenLogger, CsrfFilter.class);

		// CSRF 설정
		httpSecurity
			.csrf(csrf -> {
				csrf.csrfTokenRepository(customCsrfTokenRepository);
				csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
			});

		httpSecurity
			.httpBasic(Customizer.withDefaults());

		httpSecurity.formLogin(c -> c.defaultSuccessUrl("/main", true));


		// CORS 설정
		httpSecurity.cors(cors -> {
			CorsConfigurationSource source = request -> {
				CorsConfiguration config = new CorsConfiguration();
				// config.setAllowedOrigins(
				// 	List.of("http://localhost:48080")
				// );
				// config.setAllowedMethods(
				// 	List.of("GET", "POST", "PUT", "DELETE")
				// );
				config.setAllowedOrigins(List.of("*"));
				config.setAllowedMethods(List.of("*"));
				return config;
			};
			cors.configurationSource(source);
		});

		return httpSecurity.build();
	}
}
