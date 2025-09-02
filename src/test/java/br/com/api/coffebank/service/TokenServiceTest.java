package br.com.api.coffebank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.api.coffebank.entity.Usuario;
import br.com.api.coffebank.exception.ErroAoGerarTokenException;
import br.com.api.coffebank.exception.TokenInvalidoOuExpiradoException;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

	@InjectMocks
	private TokenService tokenService;

	@DisplayName("Deve gerar o token com sucesso.")
	@Test
	void deveGerarTokenComSucesso() {

		String secretKey = "secret";
		TokenService tokenServiceKey = new TokenService(secretKey);

		Usuario usuario = new Usuario();
		usuario.setUsername("usuarioTeste");

		String token = tokenServiceKey.gerarToken(usuario);

		assertNotNull(token);
		assertTrue(token.startsWith("ey"));

		DecodedJWT decodedJWT = JWT.decode(token);
		assertEquals("usuarioTeste", decodedJWT.getSubject());
		assertEquals("auth-api", decodedJWT.getIssuer());
	}

	@DisplayName("Deve lançar exception ao gerar o token.")
	@Test
	void deveLancarErroAoGerarToken() {

		TokenService tokenServiceKey = new TokenService(null);

		Usuario usuario = new Usuario();
		usuario.setUsername("usuarioTeste");

		assertThrows(ErroAoGerarTokenException.class, () -> {
			tokenServiceKey.gerarToken(usuario);
		});
	}
	
	@DisplayName("Deve validar token com sucesso")
	@Test
	void deveValidarTokenComSucesso() {
		
		String secretKey = "secret";
		TokenService tokenServiceKey = new TokenService(secretKey);
		
	    Usuario usuario = new Usuario();
	    usuario.setUsername("usuarioTeste");
	    
	    String token = tokenServiceKey.gerarToken(usuario);
	    String subject = tokenServiceKey.validarToken(token);
	    
	    assertEquals("usuarioTeste", subject);
	}

	@DisplayName("Deve lançar exception se o token estiver invalidou expirado.")
	@Test
	void deveLancarExceptionSeTokenInvalidoOuExpirado() {

		String secretKey = "secret";
		TokenService tokenServiceKey = new TokenService(secretKey);
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6Imx1YW4iLCJleHAiOjE3NTY4NDA5NTN9.SLc5-xNEOCIcZjn2WiGksmAg4uGaPPROcM26rb0lyhE";

		assertThrows(TokenInvalidoOuExpiradoException.class, ()->{
			tokenServiceKey.validarToken(token);
		});
	}
}
