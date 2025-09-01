package br.com.api.coffebank.service.validador;

import org.springframework.stereotype.Component;

import br.com.api.coffebank.exception.NumeroDaContaNaoExisteException;
import br.com.api.coffebank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ContaValidador {
	
	private final ContaRepository contaRepository;

	public void validaSeContaExiste(Long numeroConta) {
		if(!contaRepository.existsByCodigoCliente(numeroConta)) {
			throw new NumeroDaContaNaoExisteException();
		}
	}
}
