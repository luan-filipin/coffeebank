package br.com.api.coffebank.exception;

public class CpfUrlDiferenteDoCorpoException extends RuntimeException{

	private static final String CPF_URL_DIFERENTE_DO_CORPO = "O CPF do codigo passado precisa ser igual ao do Corpo!";
	
	public CpfUrlDiferenteDoCorpoException() {
		super(CPF_URL_DIFERENTE_DO_CORPO);
	}
}
