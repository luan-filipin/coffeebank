package br.com.api.coffebank.service.producer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import br.com.api.coffebank.dto.event.ContaEventDto;
import br.com.api.coffebank.service.event.producer.ContaProducerImp;

@ExtendWith(MockitoExtension.class)
class ContaProducerTest {

	@Mock
	private KafkaTemplate<String, ContaEventDto> kafkaTemplate;

	@InjectMocks
	private ContaProducerImp contaProducerImp;

	@DisplayName("Deve enviar o valor de deposito para o Kafka com sucesso.")
	@Test
	void deveEnviarValorDeDepositoParaKafkaComSucesso() {

		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(100);

		contaProducerImp.enviarValorDepositoKafka(codigoCliente, valor);

		verify(kafkaTemplate).send(eq("conta-deposito"), argThat(event -> event.codigoCliente().equals(codigoCliente)
				&& event.valor().equals(valor) && event.timestamp() != null));
	}

	@DisplayName("Deve lancar exception se o envio ao Kafka falhar para o deposito.")
	@Test
	void deveLancarExceptionSeEnvioFalharDoDeposito() {

		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(100);

		when(kafkaTemplate.send(anyString(), any(ContaEventDto.class)))
				.thenThrow(new RuntimeException("Falha ao enviar Kafka"));

		assertThrows(RuntimeException.class, () -> {
			contaProducerImp.enviarValorDepositoKafka(codigoCliente, valor);
		});

		verify(kafkaTemplate).send(eq("conta-deposito"), any(ContaEventDto.class));
	}

	@DisplayName("Deve enviar o valor para sacar ao Kafka com sucesso.")
	@Test
	void deveEnviarValorParaSacarAoKafkaComSucesso() {

		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(100);

		contaProducerImp.enviarValorSacarKafka(codigoCliente, valor);

		verify(kafkaTemplate).send(eq("conta-sacar"), argThat(event -> event.codigoCliente().equals(codigoCliente)
				&& event.valor().equals(valor) && event.timestamp() != null));
	}

	@DisplayName("Deve lancar exception se o envio ao Kafka falhar para sacar.")
	@Test
	void deveLancarExceptionSeEnvioFalharDoSacar() {
		Long codigoCliente = 1L;
		BigDecimal valor = BigDecimal.valueOf(100);

		when(kafkaTemplate.send(anyString(), any(ContaEventDto.class)))
				.thenThrow(new RuntimeException("Falha ao enviar Kafka"));

		assertThrows(RuntimeException.class, () -> {
			contaProducerImp.enviarValorSacarKafka(codigoCliente, valor);
		});

		verify(kafkaTemplate).send(eq("conta-sacar"), any(ContaEventDto.class));
	}
}
