package br.com.api.coffebank.service;

import java.math.BigDecimal;

public interface ContaService {

	void criarConta(Long codigoCliente);
	void deletarConta(Long codigoCliente);
	void depositarValor(Long codigoCliente, BigDecimal valor);
	void sacarValor(Long codigoCliente, BigDecimal valor);
}
