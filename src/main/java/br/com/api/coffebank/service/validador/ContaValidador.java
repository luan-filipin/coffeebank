package br.com.api.coffebank.service.validador;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import br.com.api.coffebank.entity.Conta;
import br.com.api.coffebank.exception.NumeroDaContaNaoExisteException;
import br.com.api.coffebank.exception.SaldoInsuficienteParaSaqueException;
import br.com.api.coffebank.exception.ValorNegativoException;
import br.com.api.coffebank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ContaValidador {

	private final ContaRepository contaRepository;

	public void validaSeContaExiste(Long numeroConta) {
		if (!contaRepository.existsByCodigoCliente(numeroConta)) {
			throw new NumeroDaContaNaoExisteException();
		}
	}

	public Conta validaSeContaExisteERetornaEntidade(Long codigoCliente) {
		return contaRepository.findByCodigoCliente(codigoCliente).orElseThrow(NumeroDaContaNaoExisteException::new);
	}
	
	public void validaSeValorNullOuNegativo(BigDecimal valor) {
		if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
			throw new ValorNegativoException();
		}
	}
	
	public void validaSeSaldoSuficienteParaSacar(BigDecimal saldo, BigDecimal valorSaque) {
		if(saldo.compareTo(valorSaque) < 0) {
			throw new SaldoInsuficienteParaSaqueException();
		}
	}
}
