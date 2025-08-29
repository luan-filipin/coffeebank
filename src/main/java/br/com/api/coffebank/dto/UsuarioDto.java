package br.com.api.coffebank.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioDto(
		
		@NotBlank(message = "O campo usuario é obrigatorio!") String usuario,
		@NotBlank(message = "O campo senha é obrigatorio!") String senha) {

}
