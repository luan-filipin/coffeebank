package br.com.api.coffebank.dto.resposta;

public record RespostaCriacaoUsuarioDto(String message) {
	
	public static final String CREATE_WITH_SUCESS = "Usuario criado com sucesso!";
	
    public RespostaCriacaoUsuarioDto() {
        this(CREATE_WITH_SUCESS);
    }
	

}
