package br.com.api.coffebank.dto.resposta;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhe de erro de validação de um campo específico")
public record ErroCampoDto(
		
		@Schema(description = "Nome do campo com erro", example = "cpf")
		String campo,
		
        @Schema(description = "Mensagem de validação associada ao campo", example = "CPF inválido")
		String mensagem) {

}
