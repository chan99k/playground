package chan99k.config;

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
		httpSecurity
			// .addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
			// .addFilterAfter(new LoggingFilter(), BasicAuthenticationFilter.class)
			// .addFilterAt(staticKeyAuthenticationFilter, BasicAuthenticationFilter.class)
			// .addFilterAt(testFilter, BasicAuthenticationFilter.class)
			.authorizeHttpRequests(c -> c.anyRequest().permitAll())
			.addFilterAfter(csrfTokenLogger, CsrfFilter.class)
			.httpBasic(Customizer.withDefaults())
			.csrf(csrf -> {
				csrf.csrfTokenRepository(customCsrfTokenRepository);
				csrf.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
			});


		return httpSecurity.build();
	}

	// private final CsrfTokenLogger csrfTokenLogger;
	//
	//
	// @Bean
	// public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	// 	httpSecurity.httpBasic(Customizer.withDefaults());
	// 	httpSecurity.csrf(c -> {
	// 		c.csrfTokenRepository(customCsrfTokenRepository);
	// 		c.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
	// 	});
	// 	httpSecurity.addFilterAfter(csrfTokenLogger, CsrfFilter.class);
	// 	httpSecurity.authorizeHttpRequests(c -> c.anyRequest().permitAll());
	// 	return httpSecurity.build();
	// }
}
