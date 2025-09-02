package br.com.api.coffebank.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.api.coffebank.entity.Usuario;
import br.com.api.coffebank.exception.ErroAoGerarTokenException;
import br.com.api.coffebank.exception.TokenInvalidoOuExpiradoException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {
	
	private String secretKey = "secret";
	
	public TokenService(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public String gerarToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			return JWT.create()
					.withIssuer("auth-api")
					.withSubject(usuario.getUsername())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
		} catch (JWTCreationException | IllegalArgumentException exception) {
			throw new ErroAoGerarTokenException(exception);
		}
	}
	
	public String validarToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			throw new TokenInvalidoOuExpiradoException(exception);
		}
	}
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
