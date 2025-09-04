package br.com.api.coffebank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Objeto para solicitar o token")
public record UsuarioDto(
		@Schema(description = "Username", 
        example = "Silva")
		@NotBlank(message = "O campo usuario é obrigatorio!") String username,
		
		@Schema(description = "Senha", 
        example = "123456")
		@NotBlank(message = "O campo senha é obrigatorio!") String senha) {

}
