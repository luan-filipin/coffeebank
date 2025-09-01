package br.com.api.coffebank.service.event.consumer;

import br.com.api.coffebank.dto.event.ClienteEventDto;

public interface ContaConsumer {

	void ouvirClienteCriadoKafka(ClienteEventDto event);
	void ouvirClienteDeletadoKafka(ClienteEventDto event);
	
}
