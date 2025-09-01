package br.com.api.coffebank.service.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.event.ClienteEventDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteProducerImp implements ClienteProducer{
	
	private final KafkaTemplate<String, ClienteEventDto> kafkaTemplate;

	@Override
	public void enviarClienteCriadoKafka(Long codigoCliente) {
		ClienteEventDto event = new ClienteEventDto(codigoCliente);
		kafkaTemplate.send("cliente-criado", event);
		
	}

	@Override
	public void enviarClienteDeletadoKafka(Long codigoCliente) {
		ClienteEventDto event = new ClienteEventDto(codigoCliente);
		kafkaTemplate.send("cliente-deletado", event);
	}

}
