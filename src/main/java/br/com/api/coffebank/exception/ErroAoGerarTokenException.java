package br.com.api.coffebank.exception;

public class ErroAoGerarTokenException extends RuntimeException{
	
	private static final String ERRO_AO_GERAR_TOKEN = "Erro ao gerar o token!";
	
	public ErroAoGerarTokenException(Throwable cause) {
		super(ERRO_AO_GERAR_TOKEN);
	}
	
	public ErroAoGerarTokenException() {
		super(ERRO_AO_GERAR_TOKEN);
	}

}
