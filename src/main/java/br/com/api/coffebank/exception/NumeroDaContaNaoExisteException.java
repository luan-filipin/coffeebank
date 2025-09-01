package br.com.api.coffebank.exception;

public class NumeroDaContaNaoExisteException extends RuntimeException{
	
	private static final String NUMERO_CONTA_NAO_EXISTE = "O numero da conta nao existe";
	
	public NumeroDaContaNaoExisteException() {
		super(NUMERO_CONTA_NAO_EXISTE);
	}

}
