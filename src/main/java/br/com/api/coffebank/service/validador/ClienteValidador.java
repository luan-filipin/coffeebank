package br.com.api.coffebank.service.validador;

import org.springframework.stereotype.Component;

import br.com.api.coffebank.exception.CpfJaExisteException;
import br.com.api.coffebank.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ClienteValidador {

	private final ClienteRepository clienteRepository;
	
	public void validaSeCpfJaExiste(String cpf) {
		if(clienteRepository.findByDadosPessoais_Cpf(cpf).isPresent()) {
			throw new CpfJaExisteException();
		}
	}
	
}
