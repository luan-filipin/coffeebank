package br.com.api.coffebank.service;

import br.com.api.coffebank.dto.CriarClienteDto;
import br.com.api.coffebank.dto.resposta.RespostaCriacaoClienteDto;

public interface ClienteService {

	RespostaCriacaoClienteDto criarCliente(CriarClienteDto dto);
	
}
