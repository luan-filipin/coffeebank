package br.com.api.coffebank.dto;

import br.com.api.coffebank.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Objeto para criação do usuario")
public record RequisicaoUsuarioDto(

		@Schema(description = "Username", example = "Silva")
		@NotBlank(message = "O campo usuario é obrigatorio!") 
		String username,

		@Schema(description = "Senha", example = "123456") 
		@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "A senha deve conter apenas letras e números")
		@NotBlank(message = "O campo senha é obrigatorio!") 
		String senha,

		@Schema(description = "Role para acesso", example = "ADMIN, USER") 
		@NotNull(message = "O campo role é obrigatorio!") 
		UserRole role
		) {

}
