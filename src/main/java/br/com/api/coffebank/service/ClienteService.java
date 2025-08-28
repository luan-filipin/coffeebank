package br.com.api.coffebank.service;

import br.com.api.coffebank.dto.RequisicaoClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaClienteDto;

public interface ClienteService {

	RespostaClienteDto criarCliente(RequisicaoClienteDto dto);
	
	RespostaClienteDto buscarClientePeloCodigo(Long codigo);
	
	void deletaClientePeloCodigo(Long codigo);
	
	RespostaClienteDto atualizaClientePeloCodigo(RequisicaoClienteDto dto, Long codigo);
}
