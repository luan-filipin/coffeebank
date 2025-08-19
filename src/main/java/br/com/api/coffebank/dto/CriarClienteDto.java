package br.com.api.coffebank.dto;

import jakarta.validation.Valid;

public record CriarClienteDto(
		@Valid CriarClienteDadosPessoaisDto dadosPessoais,
		@Valid CriarClienteEnderecoDto endereco) {
}
