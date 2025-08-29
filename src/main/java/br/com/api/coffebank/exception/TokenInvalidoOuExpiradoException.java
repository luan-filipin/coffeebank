package br.com.api.coffebank.exception;

public class TokenInvalidoOuExpiradoException extends RuntimeException{
	
	private static final String TOKEN_INVALIDO_OU_EXPIRADO = "Token inválido ou expirado!";
	
	public TokenInvalidoOuExpiradoException(Throwable cause) {
		super(TOKEN_INVALIDO_OU_EXPIRADO);
	}
	
	public TokenInvalidoOuExpiradoException() {
		super(TOKEN_INVALIDO_OU_EXPIRADO);
	}

}
