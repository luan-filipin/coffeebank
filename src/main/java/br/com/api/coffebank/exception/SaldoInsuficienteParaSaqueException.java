package br.com.api.coffebank.exception;

public class SaldoInsuficienteParaSaqueException extends RuntimeException{
	
	private static final String SALDO_INSUFICIENTE = "Saldo insuficiente para saque!";
	
	public SaldoInsuficienteParaSaqueException() {
		super(SALDO_INSUFICIENTE);
	}

}
