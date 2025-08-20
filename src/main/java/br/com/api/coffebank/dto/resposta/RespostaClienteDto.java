package br.com.api.coffebank.dto.resposta;

public record RespostaClienteDto(
		
		Long codigoCliente,
		RespostaClienteDadosPessoaisDto dadosPessoais,
		RespostaClienteEnderecoDto endereco) {

}
