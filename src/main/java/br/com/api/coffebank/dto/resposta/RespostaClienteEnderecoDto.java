package br.com.api.coffebank.dto.resposta;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Endereço do cliente retornado pela API")
public record RespostaClienteEnderecoDto(

		@Schema(description = "Nome da rua", example = "Rua das Flores") String rua,

		@Schema(description = "Número da residência", example = "123") String numero,

		@Schema(description = "Bairro", example = "Jardim Primavera") String bairro,

		@Schema(description = "Cidade", example = "São Paulo") String cidade,

		@Schema(description = "Complemento do endereço", example = "Apartamento 45") String complemento,

		@Schema(description = "País", example = "Brasil") String pais

) {
}