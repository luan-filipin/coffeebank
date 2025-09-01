package br.com.api.coffebank.dto;

import br.com.api.coffebank.entity.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequisicaoUsuarioDto(
		@NotBlank(message = "O campo usuario é obrigatorio!") String username,
		@NotBlank(message = "O campo senha é obrigatorio!") String senha,
		@NotNull(message = "O campo role é obrigatorio!") UserRole role) {

}
