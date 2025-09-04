package br.com.api.coffebank.dto;

import jakarta.validation.Valid;

public record RequisicaoClienteDto(
		
		@Valid RequisicaoClienteDadosPessoaisDto 
		dadosPessoais,
		
		@Valid RequisicaoClienteEnderecoDto 
		endereco
		) {
}
