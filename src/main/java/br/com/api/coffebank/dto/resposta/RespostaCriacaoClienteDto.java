package br.com.api.coffebank.dto.resposta;

public record RespostaCriacaoClienteDto(
		
		Long codigoCliente,
		RespostaCriacaoClienteDadosPessoaisDto dadosPessoais,
		RespostaCriacaoClienteEnderecoDto endereco) {

}
