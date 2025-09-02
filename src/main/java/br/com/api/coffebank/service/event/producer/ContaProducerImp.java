package br.com.api.coffebank.service.event.producer;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.event.ContaEventDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaProducerImp implements ContaProducer{
	
	private final KafkaTemplate<String, ContaEventDto> kafkaTemplate;

	@Override
	public void enviarValorDepositoKafka(Long codigoCliente, BigDecimal valor) {
		ContaEventDto event = new ContaEventDto(codigoCliente, valor, Instant.now());
		kafkaTemplate.send("conta-deposito", event);
	}

	@Override
	public void enviarValorSacarKafka(Long codigoCliente, BigDecimal valor) {
		ContaEventDto event = new ContaEventDto(codigoCliente, valor, Instant.now());
		kafkaTemplate.send("conta-sacar", event);
	}
	
}
