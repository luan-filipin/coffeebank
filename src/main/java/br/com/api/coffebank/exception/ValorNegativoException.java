package br.com.api.coffebank.exception;

public class ValorNegativoException extends RuntimeException{
	
	private static final String VALOR_NEGATIVO = "O valor não pode ser negativo!";
	
	public ValorNegativoException() {
		super(VALOR_NEGATIVO);
	}

}
