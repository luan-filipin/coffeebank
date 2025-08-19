package br.com.api.coffebank.dto.resposta;

public record ErroCampoDto(
		String campo,
		String mensagem) {

}
