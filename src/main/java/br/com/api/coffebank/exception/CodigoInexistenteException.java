package br.com.api.coffebank.exception;

public class CodigoInexistenteException extends RuntimeException{
	
	private static final String CPF_INEXISTENTE = "O cliente não existe!";
	
	public CodigoInexistenteException() {
		super(CPF_INEXISTENTE);
	}

}
