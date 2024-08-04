package com.naren.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain  securityWebFilterChain(ServerHttpSecurity httpSecurity) {
		
		httpSecurity
				.authorizeExchange()
				.anyExchange()
				.authenticated()
				.and()
				.oauth2Client()
				.and()
				.oauth2ResourceServer()
				.jwt();
		
		
		return httpSecurity.build();
	}
}
