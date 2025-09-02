package br.com.api.coffebank.service.event.producer;

import java.math.BigDecimal;

public interface ContaProducer {

	void enviarValorDepositoKafka(Long codigoCliente, BigDecimal valor);
	void enviarValorSacarKafka(Long codigoCliente, BigDecimal valor);
}
