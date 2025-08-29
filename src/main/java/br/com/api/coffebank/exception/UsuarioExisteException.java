package br.com.api.coffebank.exception;

public class UsuarioExisteException extends RuntimeException{
	
	private static final String USUARIO_EXISTE = "Usuario ja existe!";
	
	public UsuarioExisteException() {
		super(USUARIO_EXISTE);
	}

}
