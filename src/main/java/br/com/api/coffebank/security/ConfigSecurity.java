package br.com.api.coffebank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

	private static final String ENDPOINT_BASIC = "/api/cliente";
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, ENDPOINT_BASIC).permitAll()
						.requestMatchers(HttpMethod.GET, ENDPOINT_BASIC + "/**").permitAll()
						.requestMatchers(HttpMethod.DELETE,ENDPOINT_BASIC + "/**").permitAll()
						.requestMatchers(HttpMethod.PUT,ENDPOINT_BASIC + "/**").permitAll()
						.anyRequest().authenticated()
						)
				.build();
	}
}
