package br.com.api.coffebank.dto;

import jakarta.validation.constraints.NotBlank;

public record RequisicaoClienteEnderecoDto(

		@NotBlank(message = "O campo 'rua' é obrigatorio!") String rua,
		@NotBlank(message = "O campo 'numero' é obrigatorio!") String numero,
		@NotBlank(message = "O campo 'bairro' é obrigatorio!") String bairro,
		@NotBlank(message = "O campo 'cidade' é obrigatorio!") String cidade,
		@NotBlank(message = "O campo 'complemento' é obrigatorio!") String complemento,
		@NotBlank(message = "O campo 'pais' é obrigatorio!") String pais) {

}
