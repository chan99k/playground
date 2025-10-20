package chan99k.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import chan99k.security.UsernamePasswordAuthenticationProvider;
import chan99k.security.filter.InitialAuthenticationFilter;
import chan99k.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final InitialAuthenticationFilter initialAuthenticationFilter;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
		UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		httpSecurity.addFilterBefore(initialAuthenticationFilter, BasicAuthenticationFilter.class);
		httpSecurity.addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

		httpSecurity.authorizeHttpRequests(c -> c.anyRequest().permitAll());

		return httpSecurity.build();
	}
}