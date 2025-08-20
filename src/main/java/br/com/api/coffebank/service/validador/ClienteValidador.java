package br.com.api.coffebank.service.validador;

import org.springframework.stereotype.Component;

import br.com.api.coffebank.entity.Cliente;
import br.com.api.coffebank.exception.CodigoInexistenteException;
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
	
	public Cliente validaSeOCodigoDoClienteExiste(Long codigo) {
		return clienteRepository.findByCodigoCliente(codigo).orElseThrow(CodigoInexistenteException::new);
	}
	
	public void validaSeOCodigoExisteMasNaoRetornaEntity(Long codigo) {
		if(!clienteRepository.existsById(codigo)) {
			throw new CodigoInexistenteException();
		}
	}
	
}
