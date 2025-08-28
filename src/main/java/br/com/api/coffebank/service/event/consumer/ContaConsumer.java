package br.com.api.coffebank.service.event.consumer;

import br.com.api.coffebank.dto.event.ClienteCriadoEventDto;

public interface ContaConsumer {

	void ouvirClienteCriadoKafka(ClienteCriadoEventDto event);
	
}
