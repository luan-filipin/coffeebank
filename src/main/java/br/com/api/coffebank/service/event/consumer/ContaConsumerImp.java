package br.com.api.coffebank.service.event.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.api.coffebank.dto.event.ClienteEventDto;
import br.com.api.coffebank.dto.event.ContaEventDto;
import br.com.api.coffebank.service.ContaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContaConsumerImp implements ContaConsumer{
	
	private final ContaService contaService;
    private static final Logger log = LoggerFactory.getLogger(ContaConsumerImp.class);


	@KafkaListener(topics = "cliente-criado", groupId = "coffebank-group")
	@Override
	public void consumirEventoClienteCriadoKafka(ClienteEventDto event) {
		try {
			contaService.criarConta(event.codigoCliente());
		} catch (Exception e) {
			log.error("Erro ao criar conta: {}", e.getMessage());
		}
	}
	
	@KafkaListener(topics = "cliente-deletado", groupId = "coffebank-group")
	@Override
	public void consumirEventoClienteDeletadoKafka(ClienteEventDto event) {
		try {
			contaService.deletarConta(event.codigoCliente());			
		} catch (Exception e) {
			log.error("Erro ao deletar conta: {}", e.getMessage());
		}
	}

	@KafkaListener(topics = "conta-deposito", groupId = "coffebank-group")
	@Override
	public void consumirEventoDepositarValor(ContaEventDto event) {
		try {
			contaService.depositarValor(event.codigoCliente(), event.valor());
		} catch (Exception e) {
			log.error("Erro ao depositar valor: {}", e.getMessage());
		}
	}

	@KafkaListener(topics = "conta-sacar", groupId = "coffebank-group")
	@Override
	public void consumirEventoSacarValor(ContaEventDto event) {
		try {
			contaService.sacarValor(event.codigoCliente(), event.valor());		
		} catch (Exception e) {
			log.error("Erro ao sacar valor: {}", e.getMessage());
		}
	}	
}
