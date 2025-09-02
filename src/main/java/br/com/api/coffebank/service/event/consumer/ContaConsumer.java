package br.com.api.coffebank.service.event.consumer;

import br.com.api.coffebank.dto.event.ClienteEventDto;
import br.com.api.coffebank.dto.event.ContaEventDto;

public interface ContaConsumer {

	void consumirEventoClienteCriadoKafka(ClienteEventDto event);
	void consumirEventoClienteDeletadoKafka(ClienteEventDto event);
	void consumirEventoDepositarValor(ContaEventDto event);
	void consumirEventoSacarValor(ContaEventDto evet);
	
}
