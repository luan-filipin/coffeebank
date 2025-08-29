package br.com.api.coffebank.dto.resposta;

import java.time.LocalDate;

import br.com.api.coffebank.entity.enums.TipoSexo;

public record RespostaClienteDadosPessoaisDto(
		String nome,
		String email,
		TipoSexo sexo,
		String cpf,
		String telefone,
		LocalDate dataNascimento,
		String nacionalidade) {

}
