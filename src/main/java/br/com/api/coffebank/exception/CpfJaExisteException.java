package br.com.api.coffebank.exception;

public class CpfJaExisteException extends RuntimeException{

	private static final String CPF_JA_EXISTE = "Ja existe um cliente com esse CPF!";
	
	public CpfJaExisteException() {
		super(CPF_JA_EXISTE);
	}
}
