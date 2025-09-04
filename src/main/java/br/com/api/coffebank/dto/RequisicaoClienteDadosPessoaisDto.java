package br.com.api.coffebank.dto;

import java.time.LocalDate;

import br.com.api.coffebank.entity.enums.TipoSexo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Objeto para solicitar os dados pessoais")
public record RequisicaoClienteDadosPessoaisDto(
		
		@Schema(description = "Nome", example = "João Silva")
		@NotBlank(message = "O campo 'nome' é obrigatorio!") 
		String nome,
		
		@Schema(description = "Email", example = "joao@gmail.com")
		@NotBlank(message = "O campo 'email' é obrigatorio!") 
		String email,
		
		@Schema(description = "Sexo", example = "MASCULINO, FEMININO")
		@NotNull(message = "O campo 'sexo' é obrigatorio!") 
		TipoSexo sexo,
		
		@Schema(description = "Cpf", example = "123456789123")
		@NotBlank(message = "O campo 'cpf' é obrigatorio!") 
		String cpf,
		
		@Schema(description = "Telefone", example = "12455623")
	    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos")
		@NotBlank(message = "O campo 'telefone' é obrigatorio!") 
		String telefone,
		
		@Schema(description = "Data de nascimento", example = "1990-01-01")
		@NotNull(message = "O campo 'dataNascimento' é obrigatorio!") 
		LocalDate dataNascimento,
		
		@Schema(description = "Nascionalidade", example = "Brasileiro")
		@NotBlank(message = "O campo 'nacionalidade' é obrigatorio!") 
		String nacionalidade
		) {

}
