package br.com.api.coffebank.service.event.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.event.ClienteCriadoEventDto;
import br.com.api.coffebank.service.ContaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaConsumerImp implements ContaConsumer{
	
	private final ContaService contaService;

	@KafkaListener(topics = "cliente-criado", groupId = "coffebank-group")
	@Override
	public void ouvirClienteCriadoKafka(ClienteCriadoEventDto event) {
		contaService.criarConta(event.codigoCliente());
	}

}
