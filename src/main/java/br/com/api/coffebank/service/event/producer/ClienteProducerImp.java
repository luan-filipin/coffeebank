package br.com.api.coffebank.service.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.event.ClienteCriadoEventDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteProducerImp implements ClienteProducer{
	
	private final KafkaTemplate<String, ClienteCriadoEventDto> kafkaTemplate;

	@Override
	public void enviarClienteCriadoKafka(Long codigoCliente) {
		ClienteCriadoEventDto event = new ClienteCriadoEventDto(codigoCliente);
		kafkaTemplate.send("cliente-criado", event);
		
	}

}
