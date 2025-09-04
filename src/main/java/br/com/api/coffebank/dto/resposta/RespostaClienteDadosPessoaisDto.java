package br.com.api.coffebank.dto.resposta;

import java.time.LocalDate;

import br.com.api.coffebank.entity.enums.TipoSexo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados pessoais do cliente retornados pela API")
public record RespostaClienteDadosPessoaisDto(
		
	    @Schema(description = "Nome completo do cliente", example = "João Silva")
	    String nome,
	    
	    @Schema(description = "Email do cliente", example = "joao@gmail.com")
	    String email,
	    
	    @Schema(description = "Sexo do cliente", example = "MASCULINO")
	    TipoSexo sexo,
	    
	    @Schema(description = "CPF do cliente", example = "12345678900")
	    String cpf,
	    
	    @Schema(description = "Telefone do cliente", example = "DD999999999")
	    String telefone,
	    
	    @Schema(description = "Data de nascimento do cliente", example = "1990-01-01")
	    LocalDate dataNascimento,
	    
	    @Schema(description = "Nacionalidade do cliente", example = "Brasileiro")
	    String nacionalidade) {

}
