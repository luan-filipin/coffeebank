package br.com.api.coffebank.dto.resposta;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta do cliente contendo dados pessoais e endereço")
public record RespostaClienteDto(
		
	    @Schema(description = "Código único do cliente", example = "123")
	    Long codigoCliente,
	    
	    @Schema(description = "Dados pessoais do cliente")
	    RespostaClienteDadosPessoaisDto dadosPessoais,
	    
	    @Schema(description = "Endereço do cliente")
	    RespostaClienteEnderecoDto endereco) {

}




