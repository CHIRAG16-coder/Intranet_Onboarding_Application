
package com.sumpositive.onboarding.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtFilter;
	private final CustomUserDetailsService userDetailsService;
	private final CorsConfigurationSource corsConfigurationSource;

	public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomUserDetailsService userDetailsService,
			CorsConfigurationSource corsConfigurationSource) {

		this.jwtFilter = jwtFilter;
		this.userDetailsService = userDetailsService;
		this.corsConfigurationSource = corsConfigurationSource;
	}

	// Password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Authentication provider
	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

		provider.setPasswordEncoder(passwordEncoder());

		return provider;
	}

	// Authentication manager
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

		return configuration.getAuthenticationManager();
	}

	// Security filter chain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(cors -> cors.configurationSource(corsConfigurationSource))

				.csrf(csrf -> csrf.disable())

				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth

						// Swagger permissions
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

						// Public authentication APIs
						.requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

						// CORS preflight
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						// Admin APIs: ADMIN role only
						.requestMatchers("/api/admin/**").hasRole("ADMIN")

						// Chatbot requires authentication
						.requestMatchers("/api/chatbot/**").authenticated()

						// All other APIs require authentication
						.anyRequest().authenticated())

				.authenticationProvider(authenticationProvider())

				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
