package br.com.api.coffebank.exception;

public class CodigoInexistenteException extends RuntimeException{
	
	private static final String CODIGO_INEXISTENTE = "O cliente não existe!";
	
	public CodigoInexistenteException() {
		super(CODIGO_INEXISTENTE);
	}

}
