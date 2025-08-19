package br.com.api.coffebank.dto;

import java.time.LocalDate;

import br.com.api.coffebank.entity.TipoSexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarClienteDadosPessoaisDto(
		
		@NotBlank(message = "O campo 'nome' é obrigatorio!") String nome,
		@NotBlank(message = "O campo 'email' é obrigatorio!") String email,
		@NotNull(message = "O campo 'sexo' é obrigatorio!") TipoSexo sexo,
		@NotBlank(message = "O campo 'cpf' é obrigatorio!") String cpf,
		@NotBlank(message = "O campo 'telefone' é obrigatorio!") String telefone,
		@NotNull(message = "O campo 'dataNascimento' é obrigatorio!") LocalDate dataNascimento,
		@NotBlank(message = "O campo 'nacionalidade' é obrigatorio!") String nacionalidade) {

}
