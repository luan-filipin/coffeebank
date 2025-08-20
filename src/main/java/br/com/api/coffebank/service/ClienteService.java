package br.com.api.coffebank.service;

import br.com.api.coffebank.dto.CriarClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;

public interface ClienteService {

	RespostaClienteDto criarCliente(CriarClienteDto dto);
	
	RespostaClienteDto buscarClientePeloCodigo(Long codigo);
	
}
