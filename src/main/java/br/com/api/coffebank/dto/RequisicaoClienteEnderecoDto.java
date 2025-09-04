package br.com.api.coffebank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Objeto para solicitar o endereço")
public record RequisicaoClienteEnderecoDto(

		@Schema(description = "Rua", example = "Rua das Flores")
		@NotBlank(message = "O campo 'rua' é obrigatorio!") 
		String rua,
		
		@Schema(description = "Numero da casa", example = "123")
		@NotBlank(message = "O campo 'numero' é obrigatorio!") 
		String numero,
		
		@Schema(description = "Bairro", example = "Jardim Primavera")
		@NotBlank(message = "O campo 'bairro' é obrigatorio!") 
		String bairro,
		
		@Schema(description = "Cidade", example = "São Paulo")
		@NotBlank(message = "O campo 'cidade' é obrigatorio!") 
		String cidade,
		
		@Schema(description = "Complemento", example = "Apartamento 45")
		@NotBlank(message = "O campo 'complemento' é obrigatorio!") 
		String complemento,
		
		@Schema(description = "Pais", example = "Brasil")
		@NotBlank(message = "O campo 'pais' é obrigatorio!") 
		String pais
		) {

}
