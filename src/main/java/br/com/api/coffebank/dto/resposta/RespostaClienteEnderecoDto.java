package br.com.api.coffebank.dto.resposta;

public record RespostaClienteEnderecoDto(
		String rua,
		String numero,
		String bairro,
		String cidade,
		String complemento,
		String pais) {

}
