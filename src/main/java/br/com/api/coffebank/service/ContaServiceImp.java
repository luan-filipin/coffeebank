package br.com.api.coffebank.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.api.coffebank.entity.Conta;
import br.com.api.coffebank.entity.enums.TipoConta;
import br.com.api.coffebank.repository.ContaRepository;
import br.com.api.coffebank.service.validador.ContaValidador;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaServiceImp implements ContaService{

	private final ContaRepository contaRepository;
	private final ContaValidador contaValidador;

	@Transactional
	@Override
	public void criarConta(Long codigoCliente) {
		Conta conta = new Conta();
		conta.setCodigoCliente(codigoCliente);
		conta.setNumeroConta(gerarNumeroConta(codigoCliente));
		conta.setSaldo(BigDecimal.ZERO);
		conta.setTipoConta(TipoConta.CORRENTE);
		contaRepository.save(conta);
	}
	
	@Transactional
	@Override
	public void deletarConta(Long codigoCliente) {
		contaValidador.validaSeContaExiste(codigoCliente);
		contaRepository.deleteByCodigoCliente(codigoCliente);
	}
	
	
	private String gerarNumeroConta(Long codigoCliente) {
	    return String.format("%06d", codigoCliente);
	}
}
